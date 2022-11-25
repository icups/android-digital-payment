package com.mnc.app.ui.home.adapter

import com.mnc.app.R
import com.mnc.app.base.BaseSingleAdapter
import com.mnc.app.databinding.ItemFinanceBinding
import com.mnc.network.model.Finance

class FinanceAdapter : BaseSingleAdapter<Finance, ItemFinanceBinding>() {

    override fun onBindViewHolder(binding: ItemFinanceBinding, data: Finance, adapterPosition: Int) {
        binding.item = data
    }

    override fun layoutResources(): Int {
        return R.layout.item_finance
    }

    override fun usePartialScreen(): Boolean {
        return true
    }

    override fun pageWidth(): Double {
        return 0.86
    }

}