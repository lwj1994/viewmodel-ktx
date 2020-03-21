package com.lwjlol.viewmodelktx.sample

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.lwjlol.viewmodelktx.lazyActivityViewModel
import com.lwjlol.viewmodelktx.lazyFragmentViewModel

/**
 * @author luwenjie on 2020/3/21 21:55:502
 */
class ParentFragment : Fragment() {
    private val viewModel by lazyFragmentViewModel<ParentFragmentModel>()
    private val activityViewModel by lazyActivityViewModel<ActivityViewModel>()

    private val rootView by lazy {
        LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            addView(textView)
            addView(fragmentContainer)
        }
    }

    private val fragmentContainer by lazy {
        FrameLayout(requireContext()).apply {
            id = R.id.child_view_container
            layoutParams = LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        }
    }

    private val textView by lazy {
        TextView(context).apply {
            textSize = 15F
            setTextColor(Color.WHITE)
            setLineSpacing(0F,1.5F)
            setBackgroundColor(Color.BLUE)
            setPadding(100, 100, 100, 100)
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER_VERTICAL
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return rootView
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = "$viewModel \n$activityViewModel"

        childFragmentManager.beginTransaction().add(com.lwjlol.viewmodelktx.sample.R.id.child_view_container, ChildFragment(), "")
            .commitNow()
    }

    companion object {
        private const val TAG = "ParentFragment"
    }
}

class ParentFragmentModel : ViewModel() {

}