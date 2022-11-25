package com.mnc.app.ui.home.adapter

import com.mnc.app.R
import com.mnc.app.base.BaseSingleAdapter
import com.mnc.app.databinding.ItemPromoBinding
import com.mnc.network.model.Promo

class PromoAdapter : BaseSingleAdapter<Promo, ItemPromoBinding>() {

    override fun onBindViewHolder(binding: ItemPromoBinding, data: Promo, adapterPosition: Int) {
        binding.item = data
    }

    override fun layoutResources(): Int {
        return R.layout.item_promo
    }

    override fun usePartialScreen(): Boolean {
        return true
    }

    override fun pageWidth(): Double {
        return 0.8
    }

}