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
    private var _defaultError: MutableLiveData<String> = MutableLiveData()
    var _defaultLoading: MutableLiveData<Int> = MutableLiveData()

    val defaultLoading: LiveData<Int> get() = _defaultLoading
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