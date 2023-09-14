package com.example.cs426_final_project.fragments.decoration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.cs426_final_project.R
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce


class WaitingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_waiting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myProgressBar = view.findViewById<ProgressBar>(R.id.myProgressBar)
        val doubleBounce: Sprite = DoubleBounce()
        myProgressBar.indeterminateDrawable = doubleBounce
    }

    fun newInstance(): WaitingFragment? {
        return WaitingFragment()
    }
}