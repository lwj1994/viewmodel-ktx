package com.lwjlol.viewmodelktx.sample

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.lwjlol.viewmodelktx.lazyActivityViewModel
import com.lwjlol.viewmodelktx.lazyFragmentViewModel

class MainActivity : AppCompatActivity() {
    private val rootView by lazy {
        LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            addView(textView)
            addView(fragmentContainer)
        }
    }

    private val textView by lazy {
        TextView(this).apply {
            setBackgroundColor(Color.RED)
            gravity = Gravity.CENTER_VERTICAL
            textSize = 15F
            setTextColor(Color.WHITE)
            setPadding(50, 50, 50, 50)
            layoutParams = LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private val fragmentContainer by lazy {
        FrameLayout(this).apply {
            id = R.id.view_container
            layoutParams = LinearLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        }
    }

    private val viewModel by lazyActivityViewModel<ActivityViewModel>()


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(rootView)
        super.onCreate(savedInstanceState)

        textView.text = "$viewModel"

        supportFragmentManager.beginTransaction().add(com.lwjlol.viewmodelktx.sample.R.id.view_container, ParentFragment(), "")
            .commitNow()
    }
}

class ActivityViewModel : ViewModel() {
}


class SampleFragment : Fragment() {
    private val activityViewModel by lazyActivityViewModel<ActivityViewModel>()
    private val fragmentViewModel by lazyFragmentViewModel<FragmentViewModel>()
}

class FragmentViewModel : ViewModel() {

}