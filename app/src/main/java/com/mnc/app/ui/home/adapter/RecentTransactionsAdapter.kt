package com.mnc.app.ui.home.adapter

import com.mnc.app.R
import com.mnc.app.base.BaseSingleAdapter
import com.mnc.app.databinding.ItemRecentTransactionsBinding
import com.mnc.network.model.Transaction

class RecentTransactionsAdapter : BaseSingleAdapter<Transaction, ItemRecentTransactionsBinding>() {

    override fun onBindViewHolder(binding: ItemRecentTransactionsBinding, data: Transaction, adapterPosition: Int) {
        binding.item = data
    }

    override fun layoutResources(): Int {
        return R.layout.item_recent_transactions
    }

    override fun pageWidth(): Double {
        return 0.35
    }

    override fun usePartialScreen(): Boolean {
        return true
    }

}