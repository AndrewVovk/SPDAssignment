package com.getupside.spdassignment

import androidx.lifecycle.MutableLiveData
import java.net.InetAddress
import java.net.UnknownHostException
import kotlin.concurrent.thread

class ConnectivityLiveData : MutableLiveData<Boolean>() {

    fun onError() {
        if (value != false)
            checkInternetConnection(::postValue)
    }

    fun retry(onSuccess: () -> Unit) {
        checkInternetConnection {
            postValue(it)
            if (it) onSuccess()
        }
    }

    private fun checkInternetConnection(onResult: (Boolean) -> Unit) {
        thread {
            onResult(
                try {
                    val address = InetAddress.getByName("www.google.com")
                    address.toString() != ""
                } catch (e: UnknownHostException) {
                    false
                }
            )
        }
    }
}