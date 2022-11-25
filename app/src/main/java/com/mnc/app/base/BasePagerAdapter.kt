package com.mnc.app.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

abstract class BasePagerAdapter<T>(context: FragmentActivity) : FragmentStateAdapter(context) {

    private val mContents: MutableList<T> = ArrayList()

    fun initialize(list: List<T>) {
        val size = itemCount

        mContents.clear()
        notifyItemRangeRemoved(0, size)

        mContents.addAll(list)
        notifyItemRangeInserted(0, itemCount)
    }

    override fun createFragment(position: Int): Fragment {
        return initiateFragment(mContents[position], position)
    }

    abstract fun initiateFragment(data: T, index: Int): Fragment

    fun getList(): List<T> {
        return mContents
    }

    fun getPosition(index: Int): Int {
        return index + 1
    }

    fun getItem(index: Int): T? {
        return try {
            mContents[index]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun getItemCount(): Int {
        return mContents.size
    }

}