package kr.jiho.kotlinmvvm.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _isFormValid = MutableLiveData<Boolean>()

    val isFormValid: LiveData<Boolean>
        get() = _isFormValid

    fun setForm(str: String) {
        _isFormValid.value = str.isNotEmpty()
    }

    override fun onCleared() {
        super.onCleared()
    }
}