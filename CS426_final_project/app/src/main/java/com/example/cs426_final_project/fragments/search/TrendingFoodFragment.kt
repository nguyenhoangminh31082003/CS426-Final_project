package com.example.cs426_final_project.fragments.search

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cs426_final_project.MutableTypeOptionsData
import com.example.cs426_final_project.R
import com.example.cs426_final_project.TypeOptionsStatus
import com.nex3z.flowlayout.FlowLayout


class TrendingFoodFragment : Fragment() {

    interface OnFragmentInteractionListener {
        fun onChooseOption(option: String)
    }

    var onFragmentInteractionListener: OnFragmentInteractionListener? = null

    private var typeOptionsStatus: TypeOptionsStatus? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTypeOptionsStatus(view)
    }

    private fun setTypeOptionsStatus(view: View) {
        // check if null and return
        this.typeOptionsStatus = TypeOptionsStatus(MutableTypeOptionsData().toList())

        val flowLayout: FlowLayout = view.findViewById(R.id.fllSearchOption)
        flowLayout.childSpacing = 40

        val numberOfOptions: Int = this.typeOptionsStatus!!.size
        for (i in 0 until numberOfOptions) {
            val btnOption = Button(requireActivity())
            btnOption.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            val text: String = this.typeOptionsStatus!!.getTypeOption(i)
            btnOption.text = text
            // strictly wrap content
            btnOption.minWidth = 40
            btnOption.minimumWidth = 40
            btnOption.maxWidth = Int.MAX_VALUE
            if (text.length >= 16) {
                btnOption.setPadding(0, 20, 0, 20)
                btnOption.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            } else {
                btnOption.setPadding(25, 20, 25, 20)
                btnOption.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            }
            btnOption.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            btnOption.setOnClickListener {
                typeOptionsStatus!!.toggle(i)
                if (typeOptionsStatus!!.checkChosen(i)) btnOption.setBackgroundResource(R.drawable.search_page_chosen_option_customization) else btnOption.setBackgroundResource(
                    R.drawable.search_page_unchosen_option_customization
                )
                onFragmentInteractionListener?.onChooseOption(text)
            }
            btnOption.isAllCaps = false
            btnOption.setBackgroundResource(R.drawable.search_page_unchosen_option_customization)
            flowLayout.addView(btnOption)
        }
    }
}