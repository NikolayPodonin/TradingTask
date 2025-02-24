package com.podonin.main.presentation

import androidx.lifecycle.ViewModel
import com.podonin.main.navigation.MainNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    navigator: MainNavigator
) : ViewModel() {

    val screenFlow = navigator.screenFlow
}
