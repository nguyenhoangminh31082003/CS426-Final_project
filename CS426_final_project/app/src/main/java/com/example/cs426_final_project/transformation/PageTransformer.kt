package com.example.cs426_final_project.transformation

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.cs426_final_project.contracts.PageTransformerContract
import kotlin.math.abs
import kotlin.math.max

open class PageTransformer(pageTransformerContract: PageTransformerContract) :
    ViewPager2.PageTransformer {
    private val pageTransformerContract: PageTransformerContract

    init {
        this.pageTransformerContract = pageTransformerContract
    }

    override fun transformPage(view: View, position: Float) {
        pageTransformerContract.onPageTransform(view, position)
    }


}
