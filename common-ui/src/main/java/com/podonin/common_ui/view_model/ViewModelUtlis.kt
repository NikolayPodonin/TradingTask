package com.podonin.common_ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

context(ViewModel)
fun <T> Flow<T>.stateInVM(
    initial: T,
    started: SharingStarted = SharingStarted.Lazily
): StateFlow<T> {
    return this.stateIn(viewModelScope, started, initial)
}