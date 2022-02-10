package kr.jiho.kotlinmvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        // 시작화면 설정
        replaceFragment(1, R.id.home)
        binding.bottomNavigation.selectedItemId = R.id.home

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.person-> replaceFragment(0, it.itemId)
                R.id.home-> replaceFragment(1, it.itemId)
                R.id.settings-> replaceFragment(2, it.itemId)
                else -> replaceFragment(0, it.itemId)
            }

            true
        }
    }

    private fun replaceFragment(id: Int, resourceId: Int) {
        val fragment = fragArray[id]

        val transaction = supportFragmentManager.beginTransaction()

        var targetFragment = supportFragmentManager.findFragmentByTag(resourceId.toString())
        if(targetFragment == null) {
            targetFragment = fragment
            transaction.add(R.id.mainFrame, targetFragment, resourceId.toString())
        }

        // hide
        fragArray.forEach {
            transaction.hide(it)
        }

        // show
        transaction.show(targetFragment)

        transaction.commitAllowingStateLoss()

        /*
        transaction.apply {
            replace(R.id.mainFrame, fragment).commit()
        }*/
    }
}