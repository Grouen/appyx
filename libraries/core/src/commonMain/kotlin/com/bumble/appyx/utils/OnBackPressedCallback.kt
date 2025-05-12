package com.bumble.appyx.utils

abstract class OnBackPressedCallback(var isEnabled: Boolean = true) {
    abstract fun handleOnBackPressed()
}
