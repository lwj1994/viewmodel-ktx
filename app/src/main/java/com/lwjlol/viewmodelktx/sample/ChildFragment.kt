package com.lwjlol.viewmodelktx.sample

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.lwjlol.viewmodelktx.lazyActivityViewModel
import com.lwjlol.viewmodelktx.lazyFragmentViewModel
import com.lwjlol.viewmodelktx.lazyParentFragmentViewModel

/**
 * @author luwenjie on 2020/3/21 21:55:50
 */
class ChildFragment : Fragment() {
    private val viewModel by lazyFragmentViewModel<ChildFragmentModel>()
    private val activityViewModel by lazyActivityViewModel<ActivityViewModel>()
    private val parentFragmentViewModel by lazyParentFragmentViewModel<ParentFragmentModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return TextView(context).apply {
            setBackgroundColor(Color.YELLOW)
            textSize = 15F
            setTextColor(Color.RED)
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT)
            gravity = Gravity.CENTER_VERTICAL
            setLineSpacing(0F,1.5F)
            text = "$viewModel \n${parentFragmentViewModel}\n$activityViewModel"
        }
    }

    companion object {
        private const val TAG = "ParentFragment"
    }
}

class ChildFragmentModel : ViewModel() {

}