package com.mnc.app.ui.login

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mnc.app.base.BaseViewModel
import com.mnc.app.constant.Page
import com.mnc.app.repository.AuthRepository
import com.mnc.ext.gson.toJson
import com.mnc.ext.log.logError
import com.mnc.ext.log.logInfo
import com.mnc.network.model.Auth
import com.mnc.network.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository) : BaseViewModel() {

    enum class UiRequest { LOGIN }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private val mUiRequest = MutableLiveData<UiRequest>()
    val uiRequest: LiveData<UiRequest> = mUiRequest

    private val mSignIn = MutableLiveData<State<Auth>>()
    val signIn: LiveData<State<Auth>> get() = mSignIn

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    val textEmail = MutableLiveData("")
    private val email: String get() = textEmail.value.orEmpty()

    val textPassword = MutableLiveData("")
    private val password: String get() = textPassword.value.orEmpty()

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun login() {
        viewModelScope.launch {
            mSignIn.postValue(State.loading())
            try {
                repository.signIn(email, password).let { result ->
                    mSignIn.postValue(result.requireState(result.data) { repository.storeSession(it) })
                    logInfo(result.toJson(), "signInSuccess")
                }
            } catch (e: Exception) {
                mSignIn.postValue(State.error(requireErrorMessage(context, e)))
                logError(e, "signInFailure")
            }
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun clickSignIn() {
        mUiRequest.postValue(UiRequest.LOGIN)
    }

}