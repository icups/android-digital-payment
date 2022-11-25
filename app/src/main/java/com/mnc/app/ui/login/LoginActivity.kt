package com.mnc.app.ui.login

import android.app.Activity
import com.mnc.app.R
import com.mnc.app.base.BaseActivity
import com.mnc.app.databinding.ActivityLoginBinding
import com.mnc.app.ui.login.LoginViewModel.UiRequest
import com.mnc.app.ui.home.HomeActivity
import com.mnc.ext.activity.freshStart
import com.mnc.ext.activity.hideKeyboard
import com.mnc.ext.alert.showToast
import com.mnc.network.model.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>(LoginViewModel::class.java) {

    companion object {
        @JvmStatic
        fun start(context: Activity) {
            context.freshStart(LoginActivity::class.java)
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun onViewCreated() {
        binding.run {
            vm = viewModel
            setupLoading(progressCircular)
        }
    }

    override fun layoutResource(): Int {
        return R.layout.activity_login
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun setupObserver() {
        viewModel.run {
            uiRequest.observe(this@LoginActivity) {
                if (it == UiRequest.LOGIN) hideKeyboard { login() }
            }

            signIn.observe(this@LoginActivity) {
                when (it.status) {
                    Status.LOADING -> showLoading()
                    Status.SUCCESS -> hideLoading { goToHomePage() }
                    Status.ERROR -> hideLoading { showToast(it.message) }
                }
            }
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private fun goToHomePage() {
        HomeActivity.start(this)
    }

}