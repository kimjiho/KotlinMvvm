package kr.jiho.kotlinmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.jiho.kotlinmvvm.databinding.ActivityLoginBinding
import kr.jiho.kotlinmvvm.model.LoginViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var mainViewModel: LoginViewModel
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        //DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)

        mainViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)


    }
}