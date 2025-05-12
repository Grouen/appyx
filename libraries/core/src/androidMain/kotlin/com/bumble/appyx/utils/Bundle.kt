package com.bumble.appyx.utils

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import android.os.Bundle as AndroidBundle

actual fun Bundle(): Bundle = AndroidBundle().asMultiplatformBundle()

fun AndroidBundle.asMultiplatformBundle(): Bundle = MultiplatformBundle(this)

class MultiplatformBundle(private val bundle: AndroidBundle) : Bundle, Parcelable {
    override fun getSerializable(key: String): Any? {
        return bundle.getSerializable(key)
    }

    override fun putSerializable(key: String, value: Any?) {
        bundle.putSerializable(key, value as Serializable)
    }

    override fun keySet(): Set<String> {
        return bundle.keySet()
    }

    @Suppress("DEPRECATION")
    override fun get(key: String): Any? {
        return bundle.get(key)
    }

    override fun put(key: String, value: Any?) {
        bundle.put(key, value)
    }

    private fun AndroidBundle.put(key: String, value: Any?) = apply {
        when (value) {
            null -> putString(key, null) // Any nullable type will suffice.

            // Scalars
            is Boolean -> putBoolean(key, value)
            is Byte -> putByte(key, value)
            is Char -> putChar(key, value)
            is Double -> putDouble(key, value)
            is Float -> putFloat(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Short -> putShort(key, value)

            // References
            is AndroidBundle -> putBundle(key, value)
            is CharSequence -> putCharSequence(key, value)
            is Parcelable -> putParcelable(key, value)

            // Scalar arrays
            is BooleanArray -> putBooleanArray(key, value)
            is ByteArray -> putByteArray(key, value)
            is CharArray -> putCharArray(key, value)
            is DoubleArray -> putDoubleArray(key, value)
            is FloatArray -> putFloatArray(key, value)
            is IntArray -> putIntArray(key, value)
            is LongArray -> putLongArray(key, value)
            is ShortArray -> putShortArray(key, value)

            // Reference arrays
            is Array<*> -> {
                val componentType = value::class.java.componentType!!
                @Suppress("UNCHECKED_CAST") // Checked by reflection.
                when {
                    Parcelable::class.java.isAssignableFrom(componentType) -> {
                        putParcelableArray(key, value as Array<Parcelable>)
                    }
                    String::class.java.isAssignableFrom(componentType) -> {
                        putStringArray(key, value as Array<String>)
                    }
                    CharSequence::class.java.isAssignableFrom(componentType) -> {
                        putCharSequenceArray(key, value as Array<CharSequence>)
                    }
                    Serializable::class.java.isAssignableFrom(componentType) -> {
                        putSerializable(key, value)
                    }
                    else -> {
                        val valueType = componentType.canonicalName
                        throw IllegalArgumentException(
                            "Illegal value array type $valueType for key \"$key\""
                        )
                    }
                }
            }

            // Last resort. Also we must check this after Array<*> as all arrays are serializable.
            is Serializable -> putSerializable(key, value)
            else -> error("Can't put into the Bundle. $value")
        }
    }

    override fun describeContents(): Int = bundle.describeContents()
    override fun writeToParcel(dest: Parcel, flags: Int) = bundle.writeToParcel(dest, flags)

    companion object CREATOR : Parcelable.Creator<Bundle> {
        override fun createFromParcel(parcel: Parcel): Bundle {
            return AndroidBundle.CREATOR.createFromParcel(parcel).asMultiplatformBundle()
        }

        override fun newArray(size: Int): Array<Bundle?> {
            return arrayOfNulls(size)
        }
    }
}
