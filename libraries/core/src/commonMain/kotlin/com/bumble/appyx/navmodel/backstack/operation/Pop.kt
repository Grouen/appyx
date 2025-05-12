package com.bumble.appyx.navmodel.backstack.operation

import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.BackStackElements
import com.bumble.appyx.navmodel.backstack.activeIndex
import com.bumble.appyx.utils.Parcelize

/**
 * Operation:
 *
 * [A, B, C] + Pop = [A, B]
 */
@Parcelize
open class Pop<T : Any> : BackStackOperation<T> {

    override fun isApplicable(elements: BackStackElements<T>): Boolean =
        elements.any { it.targetState == BackStack.State.ACTIVE } &&
                elements.any { it.targetState == BackStack.State.STASHED }

    override fun invoke(
        elements: BackStackElements<T>
    ): BackStackElements<T> {

        val destroyIndex = elements.activeIndex
        val unStashIndex =
            elements.indexOfLast { it.targetState == BackStack.State.STASHED }
        require(destroyIndex != -1) { "Nothing to destroy, state=$elements" }
        require(unStashIndex != -1) { "Nothing to remove from stash, state=$elements" }
        return elements.mapIndexed { index, element ->
            when (index) {
                destroyIndex -> element.transitionTo(
                    newTargetState = BackStack.State.DESTROYED,
                    operation = this
                )
                unStashIndex -> element.transitionTo(
                    newTargetState = BackStack.State.ACTIVE,
                    operation = this
                )
                else -> element
            }
        }
    }

    override fun equals(other: Any?): Boolean = other is Pop<*>

    override fun hashCode(): Int = this::class.hashCode()
}

fun <T : Any> BackStack<T>.pop() {
    accept(Pop())
}
