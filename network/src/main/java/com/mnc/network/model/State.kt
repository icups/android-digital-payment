package com.mnc.network.model

data class State<out T>(val status: Status, val data: T?, val message: String?, val errorCode: Int = 0) {

    companion object {
        fun <T> success(data: T? = null): State<T> {
            return State(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T? = null, errorCode: Int = 0): State<T> {
            return State(Status.ERROR, data, msg, errorCode)
        }

        fun <T> loading(data: T? = null): State<T> {
            return State(Status.LOADING, data, null)
        }
    }

}