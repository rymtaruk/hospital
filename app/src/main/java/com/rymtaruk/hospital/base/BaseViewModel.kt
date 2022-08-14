package com.rymtaruk.hospital.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.ConnectException

open class BaseViewModel : ViewModel() {
    private val _subscribe = CompositeDisposable()
    protected var _defaultLoading: MutableLiveData<Boolean> = MutableLiveData()
    protected var _defaultError: MutableLiveData<String> = MutableLiveData()

    val defaultLoading: LiveData<Boolean> get() = _defaultLoading
    val defaultError: LiveData<String> get() = _defaultError

    protected open fun errorHandler(e: Throwable?): Boolean {
        when (e) {
            is ConnectException -> {
                _defaultError.value = "No Connection"
            }
            is HttpException -> {
                _defaultError.value = e.localizedMessage
            }
            else -> {
                _defaultError.value = "System Unavailable"
            }
        }
        return true
    }

    protected fun onDispose(disposables: Disposable?) {
        _subscribe.add(disposables!!)
    }

    override fun onCleared() {
        _subscribe.clear()
        super.onCleared()
    }
}