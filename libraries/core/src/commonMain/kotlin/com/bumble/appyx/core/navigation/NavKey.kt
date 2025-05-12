@file:OptIn(ExperimentalUuidApi::class)

package com.bumble.appyx.core.navigation

import androidx.compose.runtime.Immutable
import com.bumble.appyx.utils.Parcelable
import com.bumble.appyx.utils.Parcelize
import com.bumble.appyx.utils.RawValue
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Parcelize
@Immutable
class NavKey<NavTarget> private constructor(
    val navTarget: @RawValue NavTarget,
    val id: String
) : Parcelable {

    constructor(navTarget: @RawValue NavTarget) : this(
        navTarget = navTarget,
        id = Uuid.random().toString()
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NavKey<*>) return false

        if (navTarget != other.navTarget) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = navTarget?.hashCode() ?: 0
        result = 31 * result + id.hashCode()
        return result
    }

}
