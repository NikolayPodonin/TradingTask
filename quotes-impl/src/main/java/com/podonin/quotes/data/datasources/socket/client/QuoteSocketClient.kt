package com.podonin.quotes.data.datasources.socket.client

import com.podonin.common_io.DispatchersHolder
import com.podonin.common_io.web_sockets.BaseSocketClient
import com.podonin.common_io.web_sockets.SocketMessageMapper
import com.podonin.common_utils.mutableSharedFlow
import com.podonin.quotes.data.model.QuoteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class QuoteSocketClient @Inject constructor(
    url: String,
    messageMapper: SocketMessageMapper<QuoteEntity>,
    dispatchersHolder: DispatchersHolder
) : BaseSocketClient<QuoteEntity>(url, messageMapper) {

    private val ioScope = CoroutineScope(dispatchersHolder.io)
    private val tickerJobs = mutableMapOf<String, Pair<QuoteEntity, Job>>()
    private val mutex = Mutex()

    private val _resultFlow = mutableSharedFlow<QuoteEntity>(
        replay = 100,
        extraBufferCapacity = 100,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    override val resultFlow: SharedFlow<QuoteEntity> = _resultFlow

    override suspend fun onMessage(message: String) {
        // На каждое сообщение запускаем корутину, потому что их можно запустить тысячи
        // без memory issues
        ioScope.launch {
            val quote = messageMapper.map(message) ?: return@launch
            val job = launch { // эта корутина может выполняться долго,
                // потому что в подписчике идет запись в базу,
                // поэтому добавим ее в мапу с возможностью отменить
                _resultFlow.emit(quote)
            }
            // Даже с учетом lock эта корутина будет выполняться относительно быстро,
            // потому что работа идет с оперативной памятью
            mutex.withLock {
                tickerJobs.compute(quote.ticker) { _, oldPair ->
                    // Если ни одно из полей quote не изменилось, игнорируем,
                    // если изменилось то отменяем предыдущую корутину
                    // (в нашем quoteEntity сильно меньше полей, чем в сообщении от сервера,
                    // поэтому в теории может ничего не измениться)
                    if (quote == oldPair?.first){
                        job.cancel()
                        oldPair
                    } else {
                        oldPair?.second?.cancel()
                        quote to job
                    }
                }
            }
        }
    }

    override fun onDisconnected() {
        close()
    }
}