package com.mnc.app.base

import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
class BaseViewHolder<VDB : ViewDataBinding>(@NonNull itemBinding: ViewDataBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    val mViewDataBinding: VDB = itemBinding as VDB
}