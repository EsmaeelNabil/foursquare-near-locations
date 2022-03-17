package com.esmaeel.challenge.common.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect


abstract class BaseActivity<T : ViewDataBinding, VM : BaseViewModel>(
    private val layoutId: Int,
) : AppCompatActivity() {

    protected abstract val viewModel: VM

    /**
     *  in case we needed to access the views
     */
    lateinit var binder: T

    private lateinit var loader: LoaderDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, layoutId)
        loader = LoaderDialog[this]
        setup()
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                baseRender(it)
            }
        }
    }

    abstract fun setup()

    private fun baseRender(state: ViewState) {
        when (state) {
            is ViewState.Loading -> showLoading()
            is ViewState.Initial -> hideLoading()
            else -> {
                hideLoading()
                render(state)
            }
        }
    }

    abstract fun render(state: ViewState)

    // not private for the sake of overriding in case of custom implementation for specific screens
    open fun showLoading() {
        loader.show()
    }

    // not private for the sake of overriding in case of custom implementation for specific screens
    open fun hideLoading() {
        loader.hide()
    }

    open fun showError(errorModel: String?) {
        hideLoading()
        Toast.makeText(applicationContext, errorModel, Toast.LENGTH_SHORT).show()
    }
}
