package kr.jiho.kotlinmvvm.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.jiho.kotlinmvvm.databinding.ActivityLoginBinding
import kr.jiho.kotlinmvvm.model.LoginViewModel

class LoginActivity : AppCompatActivity() {

    val mainViewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // button disabled
        binding.btnLogin.isEnabled = false

        binding.edtId.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mainViewModel.setId(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.edtPwd.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mainViewModel.setPwd(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        // form observer
        val formObserver = Observer<Boolean> {
            binding.btnLogin.isEnabled = it
        }
        mainViewModel.isFormValid.observe(this, formObserver)

        addButtonEvent()
    }

    private fun addButtonEvent() {
        binding.btnLogin.setOnClickListener {
            // todo call api

            // todo use to datastore

            Intent(applicationContext, MainActivity::class.java).apply {
                startActivity(this)
                finishAffinity()
            }
        }

        binding.btnJoin.setOnClickListener {
            Intent(applicationContext, JoinActivity::class.java).apply {
                startActivity(this)
                finishAffinity()
            }
        }
    }
}