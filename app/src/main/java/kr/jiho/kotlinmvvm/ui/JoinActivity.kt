package kr.jiho.kotlinmvvm.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_join.*
import kr.jiho.kotlinmvvm.R
import kr.jiho.kotlinmvvm.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btn_join.setOnClickListener {
            Intent(applicationContext, LoginActivity::class.java).apply {
                startActivity(this)
                finishAffinity()
            }
        }
    }
}