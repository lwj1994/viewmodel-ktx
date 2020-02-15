 [ ![Download](https://api.bintray.com/packages/wenchieh/maven/viewmodel-ktx/images/download.svg) ](https://bintray.com/wenchieh/maven/bottombar/_latestVersion)
 ![](https://img.shields.io/badge/build-passing-green.svg)
 ![](https://img.shields.io/badge/license-MIT-orange.svg)
 
通过 `by` 委托关键字更方便的创建 ViewModel。在 Activity/Fragment `OnCreate` 的时候自动创建 `ViewMdoel`
## 使用说明

* 在 Activity 中创建：
```kotlin
class MainActivity : AppCompatActivity() {
  private val viewModel by lazyActivityViewModel<ActivityViewModel>()
  ...
}
```

* 在 Fragment 中创建：

```kotlin
class SampleFragment:Fragment(){
  private val activityViewModel by lazyActivityViewModel<ActivityViewModel>()
  private val fragmentViewModel by lazyFragmentViewModel<FragmentViewModel>()
}
```

* 在 View 中懒加载 Activity 的 ViewModel：
```kotlin
class MyView(context: Context):View(context) {
    private val viewModel by lazyActivityViewModel<ActivityViewModel>()

}
```

* 通过 View 直接获取 Activity 的 ViewModel
```kotlin
view.getActivityViewModel<ActivityViewModel>()
```



#### 传入工厂参数或者 key

每个方法都可以传入 factory 和 key 参数。注意，`factory` 和 `key` 都是 `function` 类型的。因为要懒加载，所以不能直接传入值。

```kotlin
private val viewModel by lazyActivityViewModel<ActivityViewModel>(
        factory = {
            ViewModelFactoy()
        },
        key = {
            "your key"
        }
    )
```