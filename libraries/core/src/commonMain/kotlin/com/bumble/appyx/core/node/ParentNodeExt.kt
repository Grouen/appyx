package com.bumble.appyx.core.node

import com.bumble.appyx.core.children.nodeOrNull

fun ParentNode<*>.children(): List<com.bumble.appyx.core.node.Node> {
    return children.value.values.mapNotNull { it.nodeOrNull }
}

inline fun <reified N : com.bumble.appyx.core.node.Node> ParentNode<*>.childrenOfType(): List<N> {
    return children.value.values.mapNotNull { it.nodeOrNull as? N }
}

inline fun <reified N : com.bumble.appyx.core.node.Node> ParentNode<*>.firstChildOfType(): N? {
    return children.value.values.firstOrNull { it.nodeOrNull is N }?.nodeOrNull as N?
}
