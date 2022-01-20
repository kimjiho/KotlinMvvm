package kr.jiho.kotlinmvvm.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _isFormValid = MutableLiveData<Boolean>()

    private val idValue = MutableLiveData("")
    private val pwdValue = MutableLiveData("")

    val isFormValid: LiveData<Boolean>
        get() = _isFormValid

    fun setForm() {
        _isFormValid.value = idValue.value!!.isNotEmpty() && pwdValue.value!!.isNotEmpty()

        Log.w("DEBUG", "setForm: ${_isFormValid.value}")
    }

    fun setId(str: String) {
        idValue.value = str
        setForm()
    }

    fun setPwd(str: String) {
        pwdValue.value = str
        setForm()
    }

    override fun onCleared() {
        super.onCleared()
    }
}