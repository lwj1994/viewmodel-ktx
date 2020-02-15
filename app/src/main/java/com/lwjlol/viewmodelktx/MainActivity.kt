package com.lwjlol.viewmodelktx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by lazyActivityViewModel<ActivityViewModel>()
    val view = MyView(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view.getActivityViewModel<ActivityViewModel>()
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