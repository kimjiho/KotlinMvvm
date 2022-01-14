package kr.jiho.kotlinmvvm.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _currentValue = MutableLiveData<Int>()

    val currentValue : LiveData<Int>
        get() = _currentValue

    init {
        _currentValue.value = 0
    }

    fun plusValue() {
        _currentValue.value?.plus(1)
    }

    fun minusValue() {
        _currentValue.value?.minus(1)
    }

    override fun onCleared() {
        super.onCleared()
    }
}