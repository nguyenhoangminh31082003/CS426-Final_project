package com.example.cs426_final_project.transformation

import android.view.View
import com.example.cs426_final_project.contracts.PageTransformerContract
import kotlin.math.abs

class ZoomFadePageTransformer(
    pageTransformerContract: PageTransformerContract
) : PageTransformer(pageTransformerContract) {
    companion object {
        private const val MIN_SCALE = 0.85f
        private const val MIN_ALPHA = 0.5f
        private const val BOUNDARY = 1f
    }

    override fun transformPage(view: View, position: Float) {
        super.transformPage(view, position)
        val pageWidth = view.width
        val pageHeight = view.height

        if (position < -BOUNDARY) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.alpha = 0f
        } else if (position <= BOUNDARY) { // [-1,1]
            // Modify the default slide transition to shrink the page as well.
            val scaleFactor = MIN_SCALE.coerceAtLeast(1 - abs(position))
            val vertMargin = pageHeight * (1 - scaleFactor) / 2
            val horzMargin = pageWidth * (1 - scaleFactor) / 2
            if (position < 0) {
                view.translationX = horzMargin - vertMargin / 2
            } else {
                view.translationX = -horzMargin + vertMargin / 2
            }

            // Scale the page down (between MIN_SCALE and 1).
            view.scaleX = scaleFactor
            view.scaleY = scaleFactor

            // Fade the page relative to its size.
            view.alpha = MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) /
                    (1 - MIN_SCALE) * (1 - MIN_ALPHA)
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.alpha = 0f
        }
    }
}