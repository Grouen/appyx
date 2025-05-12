package com.bumble.appyx.core.integrationpoint

import androidx.appcompat.app.AppCompatActivity
import com.bumble.appyx.utils.Bundle
import com.bumble.appyx.utils.asMultiplatformBundle

/**
 * Helper class for root [Node] integration into projects using [AppCompatActivity].
 *
 * See [NodeComponentActivity] for building upon [ComponentActivity].
 *
 * Also offers base functionality to satisfy dependencies of Android-related functionality
 * down the tree via [appyxV1IntegrationPoint]:
 * - [ActivityStarter]
 * - [PermissionRequester]
 *
 * Feel free to not extend this and use your own integration point - in this case,
 * don't forget to take a look here what methods needs to be forwarded to the root Node.
 */
open class NodeActivity : AppCompatActivity(), IntegrationPointProvider {

    override lateinit var appyxV1IntegrationPoint: ActivityIntegrationPoint
        protected set

    protected open fun createIntegrationPoint(savedInstanceState: Bundle?) =
        ActivityIntegrationPoint(
            activity = this,
            savedInstanceState = savedInstanceState
        )

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        appyxV1IntegrationPoint = createIntegrationPoint(savedInstanceState?.asMultiplatformBundle())
    }

    override fun onSaveInstanceState(outState: android.os.Bundle) {
        super.onSaveInstanceState(outState)
        appyxV1IntegrationPoint.onSaveInstanceState(outState.asMultiplatformBundle())
    }
}
