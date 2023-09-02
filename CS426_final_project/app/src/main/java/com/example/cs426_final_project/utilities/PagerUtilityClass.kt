package com.example.cs426_final_project.utilities

import android.view.View

// create enum class for swiping state
enum class SwipingState {
    SWIPING_NA,
    SWIPING_FORWARD,
    SWIPING_BACKWARD,
}
class PagerUtilityClass(
    private val pageIds: List<Int>,
) {

    private var swipingState = SwipingState.SWIPING_NA
    private fun isReadingCurrentPos(view: View, currentPosition: Int, id: Int): Boolean {
        return view.findViewById<View?>(id) != null && currentPosition == pageIds.indexOf(id)
    }

    private fun updateSwipingStatus(direction: Float) {
        val eps = 0.0f
        swipingState =
            if (direction < -eps) {
                SwipingState.SWIPING_BACKWARD
        } else if (direction < eps) {
            SwipingState.SWIPING_NA
        } else {
            SwipingState.SWIPING_FORWARD
        }
    }

    public fun getDirectionOnSwiping(view : View, position: Float, currentPosition: Int): DirectionInfoOnSwipe {
        var direction = 0f
        for (pageId in pageIds) {
            if (isReadingCurrentPos(view, currentPosition, pageId)) {
                direction = position
                updateSwipingStatus(direction)
                break
            }
        }
        return DirectionInfoOnSwipe(direction, swipingState)
    }

    public fun debugSwiping(swipingState: SwipingState, currentPosition: Int) {
        if (swipingState == SwipingState.SWIPING_BACKWARD) {
            println("$currentPosition backward")
        } else if (swipingState == SwipingState.SWIPING_FORWARD) {
            println("$currentPosition forward")
        }
    }
}

data class DirectionInfoOnSwipe(
    val direction : Float,
    val swipingState : SwipingState,
)