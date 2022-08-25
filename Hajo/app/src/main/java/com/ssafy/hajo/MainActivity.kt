package com.ssafy.hajo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ssafy.hajo.databinding.ActivityMainBinding

//import com.ssafy.hajo.plan.PlanDetailFragment
import com.ssafy.hajo.plan.PlanFragment
import com.ssafy.hajo.repository.dao.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import com.ssafy.hajo.registration.UserViewModel
import com.ssafy.hajo.util.GlobalApplication


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val userViewModel by viewModels<UserViewModel>()
    private val userRepository: UserRepository by lazy {
        UserRepository()
    }

    private val userPrefs = GlobalApplication.userPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uid = intent?.extras?.get("userUid").toString()
        val jwt = intent?.extras?.get("jwt").toString()

        userPrefs.edit().putString("uid", uid).apply()
        userPrefs.edit().putString("jwt",jwt).apply()

        Log.d("MainActivity_공통", "uid : $uid jwt : $jwt")

        this@MainActivity.userViewModel.getUserInfo()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)


        userViewModel.user.observe(this){
            if(userViewModel.user.value != null){
                val user = userViewModel.user.value
                Log.d("MainActivity_공통", "$user")
            }
        }

    }


}