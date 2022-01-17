package kr.jiho.kotlinmvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*
import kr.jiho.kotlinmvvm.databinding.ActivityLoginBinding
import kr.jiho.kotlinmvvm.model.LoginViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var mainViewModel: LoginViewModel
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        btn_login.setOnClickListener {
            Intent(applicationContext, MainActivity::class.java).apply {
                startActivity(this)
            }
        }

        btn_join.setOnClickListener {
            Intent(applicationContext, JoinActivity::class.java).apply {
                startActivity(this)
                finishAffinity()
            }
        }
    }
}