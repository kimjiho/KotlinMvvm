package kr.jiho.kotlinmvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*
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
        btn_login.isEnabled = false

        edt_id.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mainViewModel.setId(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        edt_pwd.addTextChangedListener(object: TextWatcher{
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
            btn_login.isEnabled = it
        }
        mainViewModel.isFormValid.observe(this, formObserver)

        addButtonEvent()
    }

    private fun addButtonEvent() {
        btn_login.setOnClickListener {
            // todo call api

            Intent(applicationContext, MainActivity::class.java).apply {
                startActivity(this)
                finishAffinity()
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