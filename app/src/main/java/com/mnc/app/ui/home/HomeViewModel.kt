package com.mnc.app.ui.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mnc.app.base.BaseViewModel
import com.mnc.app.repository.HomeRepository
import com.mnc.ext.gson.toJson
import com.mnc.ext.log.logError
import com.mnc.ext.log.logInfo
import com.mnc.network.model.Home
import com.mnc.network.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : BaseViewModel() {

    enum class UiRequest { FORGOT, CONTINUE_PHONE, CONTINUE_GOOGLE, CONTINUE_FACEBOOK, CONTINUE_HUAWEI }
    data class Parcel(val view: View? = null)

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private val mHome = MutableLiveData<State<Home>>()
    val home: LiveData<State<Home>> get() = mHome

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun fetchHomeData() {
        viewModelScope.launch {
            mHome.postValue(State.loading())
            try {
                repository.getHomeData().let {
                    mHome.postValue(State.success(it.data))
                    logInfo(it.toJson(), "fetchHomeDataSuccess")
                }
            } catch (e: Exception) {
                mHome.postValue(State.error(requireErrorMessage(context, e)))
                logError(e, "fetchHomeDataFailure")
            }
        }
    }

}