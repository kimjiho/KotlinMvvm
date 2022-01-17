package kr.jiho.kotlinmvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kr.jiho.kotlinmvvm.R
import kr.jiho.kotlinmvvm.databinding.ActivityMainBinding
import kr.jiho.kotlinmvvm.databinding.FirstFragmentBinding
import kr.jiho.kotlinmvvm.fragment.FirstFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firstFragment = FirstFragment()

        /*
        bottomNavigation.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener {

                when(it.itemId) {
                    0-> replaceFragment(0)
                    1-> replaceFragment(1)
                    2-> replaceFragment(2)

                    else -> replaceFragment(0)
                }
            })*/

        binding.btnLogout.setOnClickListener {
            Intent(applicationContext, LoginActivity::class.java).apply {
                startActivity(this)
                finishAffinity()
            }
        }
    }

    private fun replaceFragment(id: Int) {

        val fragment = FirstFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFrame, fragment)
        }
    }
}