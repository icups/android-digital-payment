package com.mnc.app.ui.splash

import android.annotation.SuppressLint
import com.mnc.app.R
import com.mnc.app.base.BaseActivity
import com.mnc.app.base.BaseViewModel
import com.mnc.app.constant.Times
import com.mnc.app.databinding.ActivitySplashBinding
import com.mnc.app.shared.AppPreferences
import com.mnc.app.ui.login.LoginActivity
import com.mnc.app.ui.home.HomeActivity
import com.mnc.ext.common.launchDelayedFunction
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<BaseViewModel, ActivitySplashBinding>(BaseViewModel::class.java) {

    @Inject
    lateinit var preferences: AppPreferences

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun onViewCreated() {
        launchDelayedFunction(Times.HALF_SECOND) { verifySession() }
    }

    override fun layoutResource(): Int {
        return R.layout.activity_splash
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private fun verifySession() {
        if (preferences.session?.id.isNullOrEmpty()) LoginActivity.start(this) else HomeActivity.start(this)
    }

}