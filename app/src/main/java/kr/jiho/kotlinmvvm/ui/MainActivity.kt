package kr.jiho.kotlinmvvm.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kr.jiho.kotlinmvvm.R
import kr.jiho.kotlinmvvm.databinding.ActivityMainBinding
import kr.jiho.kotlinmvvm.fragment.FirstFragment
import kr.jiho.kotlinmvvm.fragment.SecondFragment
import kr.jiho.kotlinmvvm.fragment.ThirdFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fragArray = arrayOf(FirstFragment(), SecondFragment(), ThirdFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * 대시보드
         * 채팅
         * 설정
         *
         * 네비게이션 드로워
          *  단축메뉴
          *  로그아웃
          *  설정
         * */


        // 시작화면 설정
        replaceFragment(1)
        bottomNavigation.selectedItemId = R.id.home

        bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.person-> replaceFragment(0)
                R.id.home-> replaceFragment(1)
                R.id.settings-> replaceFragment(2)
                else -> replaceFragment(0)
            }

            true
        }

        binding.btnLogout.setOnClickListener {
            Intent(applicationContext, LoginActivity::class.java).apply {
                startActivity(this)
                finishAffinity()
            }
        }
    }

    private fun replaceFragment(id: Int) {
        val fragment = fragArray[id]

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFrame, fragment).commit()
        }
    }
}