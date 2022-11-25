package com.mnc.app.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mnc.app.R
import com.mnc.ext.display.screenWidth
import com.mnc.ext.inflater.getLayoutInflater
import com.mnc.ext.log.logError

@Suppress("unused")
abstract class BaseSingleAdapter<T, VDB : ViewDataBinding>(protected val mList: MutableList<T> = ArrayList()) : RecyclerView.Adapter<BaseViewHolder<VDB>>() {

    lateinit var adapterContext: Context

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    var activity: AppCompatActivity? = null
    private var lastAnimatedPosition = -1

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private val isFullSingleItem get() = if (fullSingleItem()) mList.size > 1 else true

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VDB> {
        val updatedWidth = (screenWidth * pageWidth()).toInt()

        return BaseViewHolder(DataBindingUtil.inflate<VDB>(getLayoutInflater(parent), layoutResources(), parent, false).apply {
            if (usePartialScreen() && isFullSingleItem) root.updateLayoutParams { width = updatedWidth }
            adapterContext = parent.context
        })
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VDB>, position: Int) {
        if (mList.isEmpty()) return

        holder.apply {
            try {
                val data = getData(position)

                onBindViewHolder(mViewDataBinding, data, position)
                if (animated()) setupAnimation(mViewDataBinding, position, animResources())

                setupListener(mViewDataBinding, data, position)
                setupSecondaryAdapter(mViewDataBinding, data, position)
            } catch (e: Exception) {
                logError(e, "BaseSingleAdapter")
            }
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun getItemCount(): Int {
        return mList.size
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun initialize(contents: List<T>, ifEmpty: (() -> Unit)? = null, ifNotEmpty: (() -> Unit)? = null) {
        val size = itemCount

        mList.clear()
        notifyItemRangeRemoved(0, size)

        mList.addAll(contents)
        notifyItemRangeInserted(0, itemCount)

        if (mList.isEmpty()) ifEmpty?.invoke()
        else ifNotEmpty?.invoke()
    }

    fun setup(activity: Activity?) {
        this.activity = activity as AppCompatActivity
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun add(content: T) {
        mList.add(content)
        notifyItemInserted(itemCount)
    }

    fun add(index: Int, content: T) {
        mList.add(index, content)
        notifyItemInserted(index)
    }

    fun addAll(contents: List<T>) {
        val size = itemCount
        mList.addAll(contents)
        notifyItemRangeInserted(size, itemCount)
    }

    fun remove(position: Int, action: (() -> Unit)? = null, ifEmpty: (() -> Unit)? = null) {
        try {
            mList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
            action?.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (mList.isEmpty()) ifEmpty?.invoke()
    }

    fun update(position: Int, content: T) {
        mList[position] = content
        notifyItemChanged(position)
    }

    fun clear() {
        val size = itemCount
        mList.clear()
        notifyItemRangeRemoved(0, size)
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun getIndexOf(item: T?): Int {
        if (item == null) return 0
        return mList.indexOf(item)
    }

    private fun getData(position: Int = 0): T {
        return try {
            mList[position]
        } catch (e: Exception) {
            mList.last()
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    val firstItem: T? get() = mList.firstOrNull()
    val lastItem: T? get() = mList.lastOrNull()

    val empty: Boolean get() = mList.isEmpty()
    val notEmpty: Boolean get() = mList.isNotEmpty()

    val list: List<T> get() = mList.toList()
    val size: Int get() = mList.size

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected abstract fun onBindViewHolder(binding: VDB, data: T, adapterPosition: Int)

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected open fun setupListener(binding: VDB, data: T, adapterPosition: Int) {}
    protected open fun setupSecondaryAdapter(binding: VDB, data: T, adapterPosition: Int) {}

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private fun setupAnimation(binding: VDB, adapterPosition: Int, @AnimRes resId: Int) {
        binding.root.run {
            if (adapterPosition > lastAnimatedPosition) {
                animation = AnimationUtils.loadAnimation(adapterContext, resId)
                also { it.animation.start() }
                lastAnimatedPosition = adapterPosition
            } else {
                animation?.cancel()
                clearAnimation()
            }
        }
    }

    protected open fun animResources(): Int = R.anim.slide_in_bottom
    protected open fun animated(): Boolean = false

    protected open fun pageWidth(): Double = 0.8
    protected open fun usePartialScreen(): Boolean = false
    protected open fun fullSingleItem(): Boolean = true

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected abstract fun layoutResources(): Int

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected fun <T> startActivity(activityClass: Class<T>, bundle: Bundle? = null) {
        adapterContext.run {
            startActivity(Intent(this, activityClass).apply { bundle?.run { putExtras(this) } })
        }
    }

}
