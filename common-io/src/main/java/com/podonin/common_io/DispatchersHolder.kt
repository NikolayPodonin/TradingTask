package com.podonin.common_io

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface DispatchersHolder {
    val io: CoroutineDispatcher
    val calc: CoroutineDispatcher
    val main: CoroutineDispatcher
}

class DispatchersHolderImpl @Inject constructor(): DispatchersHolder {
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val calc: CoroutineDispatcher = Dispatchers.Default
    override val main: CoroutineDispatcher = Dispatchers.Main
}