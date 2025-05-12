package com.bumble.appyx.core.plugin

import androidx.lifecycle.Lifecycle
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.state.MutableSavedStateMap
import com.bumble.appyx.utils.OnBackPressedCallback

interface Plugin

inline fun <reified P : Plugin> com.bumble.appyx.core.node.Node.plugins(): List<P> =
    this.plugins.filterIsInstance<P>()

interface NodeAware<N : com.bumble.appyx.core.node.Node> : NodeReadyObserver<N> {
    val node: N
}

interface NodeReadyObserver<N : com.bumble.appyx.core.node.Node> : Plugin {
    fun init(node: N) {}
}

interface NodeLifecycleAware : Plugin {
    fun onCreate(lifecycle: Lifecycle) {}
}

interface GlobalNodeLifecycleAware : Plugin {
    fun onCreate(node: com.bumble.appyx.core.node.Node, lifecycle: Lifecycle) {}
    fun onDestroy(node: com.bumble.appyx.core.node.Node) {}
}

interface UpNavigationHandler : Plugin {
    fun handleUpNavigation(): Boolean = false
}

fun interface Destroyable : Plugin {
    fun destroy()
}

/**
 * Implementing class can handle back presses via [OnBackPressedCallback].
 *
 * Implement either [onBackPressedCallback] or [onBackPressedCallbackList], not both.
 * In case if both implemented, [onBackPressedCallback] will be ignored.
 * There is runtime check in [Node] to verify correctness.
 */
interface BackPressHandler : Plugin {

    val onBackPressedCallback: OnBackPressedCallback? get() = null

    /** It is impossible to combine multiple [OnBackPressedCallback] into the single one, they are not observable. */
    val onBackPressedCallbackList: List<OnBackPressedCallback>
        get() = listOfNotNull(onBackPressedCallback)

    fun handleOnBackPressed(): Boolean =
        onBackPressedCallbackList.any { callback ->
            val isEnabled = callback.isEnabled
            if (isEnabled) callback.handleOnBackPressed()
            isEnabled
        }

}

/**
 * Bundle for future state restoration.
 * Result should be supported by [androidx.compose.runtime.saveable.SaverScope.canBeSaved].
 */
interface SavesInstanceState : Plugin {
    fun saveInstanceState(state: MutableSavedStateMap) {}
}
