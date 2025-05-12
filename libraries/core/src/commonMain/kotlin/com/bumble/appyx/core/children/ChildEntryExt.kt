package com.bumble.appyx.core.children

import com.bumble.appyx.core.node.Node

val <T> ChildEntry<T>.nodeOrNull: com.bumble.appyx.core.node.Node?
    get() =
        when (this) {
            is ChildEntry.Initialized -> node
            is ChildEntry.Suspended -> null
        }
