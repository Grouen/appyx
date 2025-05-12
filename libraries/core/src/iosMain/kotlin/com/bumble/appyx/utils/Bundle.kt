package com.bumble.appyx.utils

actual fun Bundle(): Bundle = IOSBundle()

class IOSBundle : Bundle {
    private val map = mutableMapOf<String, Any?>()
    override fun getSerializable(key: String): Any? = map[key]
    override fun putSerializable(key: String, value: Any?) {
        map[key] = value
    }
    override fun keySet(): Set<String> = map.keys
    override fun get(key: String): Any? = map[key]
    override fun put(key: String, value: Any?) {
        map.put(key, value)
    }
}
