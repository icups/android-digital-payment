package com.mnc.app.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.mnc.ext.alert.showToast
import com.mnc.ext.common.launchDelayedFunction
import com.mnc.ext.refresh.finish
import com.mnc.ext.refresh.refresh
import com.mnc.ext.view.gone
import com.mnc.ext.view.hide
import com.mnc.ext.view.reveal
import com.mnc.ext.view.rotate

abstract class BaseActivity<VM : BaseViewModel, VDB : ViewDataBinding>(private val viewModelClass: Class<VM>) : AppCompatActivity() {

    protected lateinit var viewModel: VM
    protected lateinit var binding: VDB

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private var loadingView: View? = null

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected var onProcess: Boolean = false

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun onCreate(savedInstanceState: Bundle?) {
        setupOrientation()
        super.onCreate(savedInstanceState)

        setupViewModel()
        setupArguments()

        attachViewDataBinding()
        setupAuth()

        setupViewPager()
        setupAdapter()
        setupObserver()

        onViewCreated()

        launchDelayedFunction(250) {
            initAPI()
            setupListener()
        }
    }

    private fun attachViewDataBinding() {
        DataBindingUtil.setContentView<VDB>(this@BaseActivity, layoutResource()).apply {
            setFinishOnTouchOutside(false)
            binding = this
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    @SuppressLint("SourceLockedOrientationActivity")
    private fun setupOrientation() {
        requestedOrientation = if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(viewModelClass)
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected abstract fun onViewCreated()

    protected open fun setupLocation() {}
    protected open fun setupArguments() {}
    protected open fun setupAuth() {}
    protected open fun setupAdapter() {}
    protected open fun setupViewPager() {}

    protected open fun setupListener() {}
    protected open fun setupObserver() {}

    protected open fun initAPI() {}

    protected open fun popUpBelowToolbar(): Boolean {
        return true
    }

    protected open fun navResource(): Int {
        return 0
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected fun setupLoading(view: View) {
        loadingView = view
    }

    protected fun setupToolbar(toolbar: Toolbar, showHomeAsUp: Boolean = true) {
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(showHomeAsUp)

        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected fun showLoading(view: View? = null, action: (() -> Unit)? = null) {
        if (view != null) setupLoading(view)

        if (loadingView != null) {
            when (loadingView) {
                is SwipeRefreshLayout -> (loadingView as SwipeRefreshLayout).refresh()
                else -> loadingView.reveal()
            }
            action?.invoke()
        } else {
            showToast("Please setup your loading view!")
        }
    }

    protected open fun hideLoading(delay: Long = 0, action: (() -> Unit)? = null) {
        launchDelayedFunction(delay) {
            if (loadingView != null) {
                when (loadingView) {
                    is SwipeRefreshLayout -> (loadingView as SwipeRefreshLayout).finish()
                    else -> loadingView.gone()
                }
                action?.invoke()
            } else {
                showToast("Please setup your loading view!")
            }
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected fun ShimmerFrameLayout.showShimmer(vararg views: View, action: (() -> Unit)? = null) {
        views.forEach { it.hide() }
        startShimmer()
        reveal()
        action?.invoke()
    }

    protected fun ShimmerFrameLayout.hideShimmer(vararg views: View, action: (() -> Unit)? = null) {
        stopShimmer()
        hide()
        views.forEach { it.reveal() }
        action?.invoke()
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected fun View.toggle(expanded: Boolean) {
        rotate(if (expanded) 0f else 180f, if (expanded) 180f else 0f)
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected fun finishWith(resultCode: Int, bundle: Bundle? = null) {
        setResult(resultCode, intent.apply { bundle?.run { putExtras(this) } })
        finish()
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    @LayoutRes
    protected abstract fun layoutResource(): Int

}
