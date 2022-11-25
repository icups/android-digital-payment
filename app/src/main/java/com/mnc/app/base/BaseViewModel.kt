package com.mnc.app.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.mnc.app.shared.ResourceProvider
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    @Inject
    lateinit var context: Application

    fun requireErrorMessage(context: Context, exception: Exception): String {
        val provider = ResourceProvider(context)
        exception.printStackTrace()

        return when (exception) {
            is SocketTimeoutException -> provider.timeout
            is HttpException -> handleHttpException(provider, exception)
            is UnknownHostException -> provider.networkError
            else -> exception.message.orEmpty()
        }
    }

    private fun handleHttpException(res: ResourceProvider, exception: HttpException): String {
        return when (exception.code()) {
            401 -> "Wrong Email or Password"
            else -> res.generalError
        }
    }

}