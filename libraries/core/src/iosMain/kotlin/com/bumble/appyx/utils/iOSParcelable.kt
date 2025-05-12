package com.bumble.appyx.utils

actual interface Parcelable

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
actual annotation class IgnoredOnParcel

@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.BINARY)
actual annotation class RawValue
