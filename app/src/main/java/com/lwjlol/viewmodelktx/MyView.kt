package com.lwjlol.viewmodelktx

import android.content.Context
import android.view.View

/**
 * @author luwenjie on 2020/2/15 09:21:12
 */
class MyView(context: Context):View(context) {
    private val viewModel by lazyActivityViewModel<ActivityViewModel>()

}