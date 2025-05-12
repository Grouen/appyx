package com.bumble.appyx.core.integration

import androidx.compose.runtime.Stable
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node

@Stable
fun interface NodeFactory<N : com.bumble.appyx.core.node.Node> {
    fun create(buildContext: BuildContext): N
}
