package com.bumble.appyx.core.node

fun <T : com.bumble.appyx.core.node.Node> T.build(): T = also { it.onBuilt() }
