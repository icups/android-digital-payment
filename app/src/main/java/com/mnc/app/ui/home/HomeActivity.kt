package com.mnc.app.ui.home

import android.app.Activity
import com.mnc.app.R
import com.mnc.app.base.BaseActivity
import com.mnc.app.databinding.ActivityHomeBinding
import com.mnc.app.shared.AppPreferences
import com.mnc.app.ui.home.adapter.FinanceAdapter
import com.mnc.app.ui.home.adapter.RecentTransactionsAdapter
import com.mnc.app.ui.home.adapter.PromoAdapter
import com.mnc.ext.activity.freshStart
import com.mnc.ext.alert.showToast
import com.mnc.network.model.Home
import com.mnc.network.model.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>(HomeViewModel::class.java) {

    @Inject
    lateinit var preferences: AppPreferences

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    companion object {
        @JvmStatic
        fun start(context: Activity) {
            context.freshStart(HomeActivity::class.java)
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private val financeAdapter by lazy { FinanceAdapter() }
    private val recentTransactionsAdapter by lazy { RecentTransactionsAdapter() }
    private val promoAdapter by lazy { PromoAdapter() }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun onViewCreated() {
        binding.run {
            item = preferences.session
            vm = viewModel

            setupLoading(progressCircular)
        }
    }

    override fun layoutResource(): Int {
        return R.layout.activity_home
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun initAPI() {
        viewModel.fetchHomeData()
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun setupAdapter() {
        binding.run {
            recyclerFinance.adapter = financeAdapter
            recyclerTransactions.adapter = recentTransactionsAdapter
            recyclerPromotion.adapter = promoAdapter
        }
    }

    override fun setupListener() {
        binding.run {
            swiperHome.setOnRefreshListener {
                setupLoading(swiperHome)
                initAPI()
            }
        }
    }

    override fun setupObserver() {
        viewModel.run {
            home.observe(this@HomeActivity) {
                when (it.status) {
                    Status.LOADING -> showLoading()
                    Status.SUCCESS -> hideLoading { it.data?.collectData() }
                    Status.ERROR -> hideLoading { showToast(it.message) }
                }
            }
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private fun Home.collectData() {
        financeAdapter.initialize(cards)
        recentTransactionsAdapter.initialize(transactions)
        promoAdapter.initialize(promo)
    }

}