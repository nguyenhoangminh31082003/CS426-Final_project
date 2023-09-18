package com.example.cs426_final_project.utilities

import android.view.View

/*
    Create enum class for swiping state
*/
enum class SwipingState {
    SWIPING_NA,
    SWIPING_FORWARD,
    SWIPING_BACKWARD,
}
class PagerUtilityClass(
    private val pageIds: List<Int>,
) {
    private var direction = 0f
    private var swipingState = SwipingState.SWIPING_NA
    private fun isReadingCurrentPos(view: View, currentPosition: Int, id: Int): Boolean {
        return view.findViewById<View?>(id) != null && pageIds[currentPosition] == id
    }

    private fun updateSwipingStatus(direction: Float) {
        val eps = 0.01f
        swipingState =
            if (direction < -eps) {
                SwipingState.SWIPING_BACKWARD
        } else if (direction < eps) {
            SwipingState.SWIPING_FORWARD
        } else {
            SwipingState.SWIPING_NA
        }
    }


    fun getDirectionOnSwiping(view : View, position: Float, currentPosition: Int): DirectionInfoOnSwipe {

        for (pageId in pageIds) {
            if (isReadingCurrentPos(view, currentPosition, pageId)) {
                direction = position
                updateSwipingStatus(direction)
                break
            }
        }
        print("direction from getDirectionOnSwiping: $direction\n")
        return DirectionInfoOnSwipe(direction, swipingState)
    }

    fun debugSwiping(swipingState: SwipingState, currentPosition: Int) {
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

