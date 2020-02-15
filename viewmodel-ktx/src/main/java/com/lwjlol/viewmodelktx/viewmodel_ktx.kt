package com.lwjlol.viewmodelktx

import android.view.View
import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import java.io.Serializable


inline fun <reified VM : ViewModel> FragmentActivity.lazyActivityViewModel(
    crossinline factory: () -> ViewModelProvider.NewInstanceFactory? = { null },
    crossinline key: () -> String? = { null }
): Lazy<VM> = LifecycleAwareLazy(this) {
    val factoryValue = factory() ?: defaultViewModelProviderFactory
    val keyValue = key()
    if (keyValue == null) {
        ViewModelProvider(viewModelStore, factoryValue).get(VM::class.java)
    } else {
        ViewModelProvider(viewModelStore, factoryValue).get(keyValue, VM::class.java)
    }
}


inline fun <reified VM : ViewModel> Fragment.lazyActivityViewModel(
    crossinline factory: () -> ViewModelProvider.NewInstanceFactory? = { null },
    crossinline key: () -> String? = { null }): Lazy<VM> = LifecycleAwareLazy(this) {
    val factoryValue = factory() ?: defaultViewModelProviderFactory
    val keyValue = key()
    if (keyValue == null) {
        ViewModelProvider(requireActivity().viewModelStore, factoryValue).get(VM::class.java)
    } else {
        ViewModelProvider(requireActivity().viewModelStore, factoryValue).get(keyValue, VM::class.java)
    }
}

inline fun <reified VM : ViewModel> Fragment.lazyFragmentViewModel(
    crossinline factory: () -> ViewModelProvider.NewInstanceFactory? = { null },
    crossinline key: () -> String? = { null }): Lazy<VM> = LifecycleAwareLazy(this) {
    val factoryValue = factory() ?: defaultViewModelProviderFactory
    val keyValue = key()
    if (keyValue == null) {
        ViewModelProvider(viewModelStore, factoryValue).get(VM::class.java)
    } else {
        ViewModelProvider(viewModelStore, factoryValue).get(keyValue, VM::class.java)
    }
}

inline fun <reified VM : ViewModel> View.lazyActivityViewModel(
    crossinline factory: () -> ViewModelProvider.NewInstanceFactory? = { null }, crossinline key: () -> String? = { null }): Lazy<VM> = lazy(
    LazyThreadSafetyMode.NONE
) {
    val context = context as? FragmentActivity ?: throw IllegalStateException("view context is not FragmentActivity")
    val factoryValue = factory() ?: context.defaultViewModelProviderFactory
    val keyValue = key()
    if (keyValue == null) {
        ViewModelProvider(context.viewModelStore, factoryValue).get(VM::class.java)
    } else {
        ViewModelProvider(context.viewModelStore, factoryValue).get(keyValue, VM::class.java)
    }
}

inline fun <reified VM : ViewModel> View.getActivityViewModel(
    factory: ViewModelProvider.Factory? = null, key: String? = null): VM {
    val context = context as? FragmentActivity ?: throw IllegalStateException("view context is not FragmentActivity")
    val f = factory ?: context.defaultViewModelProviderFactory
    return if (key == null) {
        ViewModelProvider(context.viewModelStore, f).get(VM::class.java)
    } else {
        ViewModelProvider(context.viewModelStore, f).get(key, VM::class.java)
    }
}




private object UninitializedValue

/**
 * This was copied from SynchronizedLazyImpl but modified to automatically initialize in ON_CREATE.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
@SuppressWarnings("Detekt.ClassNaming")
class LifecycleAwareLazy<out T>(private val owner: LifecycleOwner, initializer: () -> T) : Lazy<T>,
    Serializable {
    private var initializer: (() -> T)? = initializer
    @Volatile
    @SuppressWarnings("Detekt.VariableNaming")
    private var _value: Any? = UninitializedValue
    // final field is required to enable safe publication of constructed instance
    private val lock = this

    init {
        owner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onStart() {
                if (!isInitialized()) value
                owner.lifecycle.removeObserver(this)
            }
        })
    }

    @Suppress("LocalVariableName")
    override val value: T
        get() {
            @SuppressWarnings("Detekt.VariableNaming")
            val _v1 = _value
            if (_v1 !== UninitializedValue) {
                @Suppress("UNCHECKED_CAST")
                return _v1 as T
            }

            return synchronized(lock) {
                @SuppressWarnings("Detekt.VariableNaming")
                val _v2 = _value
                if (_v2 !== UninitializedValue) {
                    @Suppress("UNCHECKED_CAST") (_v2 as T)
                } else {
                    val typedValue = initializer!!()
                    _value = typedValue
                    initializer = null
                    typedValue
                }
            }
        }

    override fun isInitialized(): Boolean = _value !== UninitializedValue

    override fun toString(): String =
        if (isInitialized()) value.toString() else "Lazy value not initialized yet."
}