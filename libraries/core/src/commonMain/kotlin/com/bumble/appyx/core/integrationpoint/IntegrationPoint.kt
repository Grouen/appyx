package com.bumble.appyx.core.integrationpoint

import androidx.compose.runtime.Stable
import com.bumble.appyx.core.integrationpoint.requestcode.RequestCodeRegistry
import com.bumble.appyx.core.navigation.upnavigation.UpNavigationHandler
import com.bumble.appyx.utils.Bundle

@Stable
abstract class IntegrationPoint(
    protected val savedInstanceState: Bundle?
) : UpNavigationHandler {

    protected val requestCodeRegistry = RequestCodeRegistry(savedInstanceState)

    abstract val isChangingConfigurations: Boolean

    fun onSaveInstanceState(outState: Bundle) {
        requestCodeRegistry.onSaveInstanceState(outState)
    }

    abstract fun onRootFinished()
}
