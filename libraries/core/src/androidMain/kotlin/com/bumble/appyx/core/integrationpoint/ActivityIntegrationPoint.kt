package com.bumble.appyx.core.integrationpoint

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import com.bumble.appyx.utils.Bundle

open class ActivityIntegrationPoint(
    private val activity: Activity,
    savedInstanceState: Bundle?,
) : IntegrationPoint(savedInstanceState = savedInstanceState) {
    override val isChangingConfigurations: Boolean
        get() = activity.isChangingConfigurations

    override fun handleUpNavigation() {
        if (!activity.onNavigateUp()) {
            activity.onBackPressed()
        }
    }

    override fun onRootFinished() {
        if (!activity.onNavigateUp()) {
            activity.finish()
        }
    }

    companion object {
        fun getIntegrationPoint(context: Context): IntegrationPoint {
            val activity = context.findActivity<Activity>()
            checkNotNull(activity) {
                "Could not find an activity from the context: $context"
            }

            val integrationPointProvider = activity as? IntegrationPointProvider ?: error(
                "Activity ${activity::class.qualifiedName} does not implement IntegrationPointProvider"
            )

            return integrationPointProvider.appyxV1IntegrationPoint
        }

        @Suppress("UNCHECKED_CAST")
        private fun <T : Activity> Context.findActivity(): T? {
            return if (this is Activity) {
                this as T?
            } else {
                val contextWrapper = this as ContextWrapper?
                val baseContext = contextWrapper?.baseContext
                requireNotNull(baseContext)
                baseContext.findActivity()
            }
        }
    }
}
