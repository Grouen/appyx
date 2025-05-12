package com.bumble.appyx.utils

expect fun Bundle(): Bundle

interface Bundle {
    fun getSerializable(key: String): Any?
    fun putSerializable(key: String, value: Any?)
    fun keySet(): Set<String>
    fun get(key: String): Any?
    fun put(key: String, value: Any?)
}
