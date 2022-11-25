package com.mnc.app.base

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.mnc.app.R
import com.mnc.app.constant.Times
import com.mnc.ext.alert.showToast
import com.mnc.ext.common.launchDelayedFunction
import com.mnc.ext.refresh.finish
import com.mnc.ext.refresh.refresh
import com.mnc.ext.view.gone
import com.mnc.ext.view.hide
import com.mnc.ext.view.reveal

@Suppress("unused")
abstract class BaseFragment<VM : BaseViewModel, VDB : ViewDataBinding>(private val viewModelClass: Class<VM>) : Fragment() {

    lateinit var viewModel: VM
    lateinit var binding: VDB

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private var loadingView: View? = null

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected var onProcess: Boolean = false

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResource(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupArguments()

        setupView()
        setupAdapter()

        setupBaseObserver()
        setupObserver()

        onViewCreated()
        initAPI()

        launchDelayedFunction(Times.HALF_SECOND) { setupListener() }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[viewModelClass]
    }

    private fun setupBaseObserver() {
        viewModel.run {}
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected abstract fun onViewCreated()

    protected open fun setupArguments() {}
    protected open fun setupView() {}
    protected open fun setupAdapter() {}
    protected open fun setupListener() {}
    protected open fun setupObserver() {}

    protected open fun initAPI() {}

    protected fun setupLoading(view: View) {
        loadingView = view
    }

    protected fun setupToolbar(toolbar: Toolbar, showHomeAsUp: Boolean = true) {
        (activity as AppCompatActivity?)?.run {
            setSupportActionBar(toolbar)

            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(showHomeAsUp)
        }

        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    protected open fun popUpBelowToolbar(): Boolean {
        return true
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected open fun showLoading(view: View? = null, action: (() -> Unit)? = null) {
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

    @Suppress("SameParameterValue")
    protected fun setResult(resultCode: Int, bundle: Bundle? = null) {
        activity?.setResult(resultCode, Intent().apply { bundle?.run { putExtras(this) } })
    }

    protected fun setResultOK(bundle: Bundle? = null, action: (() -> Unit)? = null) {
        activity?.run { setResult(RESULT_OK, bundle) }
        action?.invoke()
    }

    protected fun finish() {
        activity?.finish()
    }

    protected fun finishWith(resultCode: Int, bundle: Bundle? = null) {
        activity?.run {
            setResult(resultCode, intent.apply { bundle?.run { putExtras(this) } })
            finish()
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected fun underDevelopment() = showToast(getString(R.string.under_development))

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    @LayoutRes
    protected abstract fun layoutResource(): Int

}
