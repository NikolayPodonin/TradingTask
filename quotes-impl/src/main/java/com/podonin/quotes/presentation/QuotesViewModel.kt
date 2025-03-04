package com.podonin.quotes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podonin.common_io.DispatchersHolder
import com.podonin.common_ui.view_model.stateInVM
import com.podonin.quotes.domain.QuotesInteractor
import com.podonin.quotes.presentation.ui.mapper.QuoteItemMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    dispatchersHolder: DispatchersHolder,
    quotesInteractor: QuotesInteractor,
    private val quoteItemMapper: QuoteItemMapper,
) : ViewModel() {

    val quotesFlow = quotesInteractor
        .getQuotesFlow()
        .map { quotes -> quoteItemMapper.mapToItems(quotes) }
        .debounce(200)
        .flowOn(dispatchersHolder.calc)
        .stateInVM(initial = emptyList())

    val errorFlow = quotesInteractor.getErrorFlow()

    init {
        viewModelScope.launch {
            quotesInteractor.subscribeOnQuotes()
        }
    }
}
