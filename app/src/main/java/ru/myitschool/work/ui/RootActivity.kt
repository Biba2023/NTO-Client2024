package ru.myitschool.work.ui

//import android.R
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.myitschool.work.R


// НЕ ИЗМЕНЯЙТЕ НАЗВАНИЕ КЛАССА!
@AndroidEntryPoint
class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ru.myitschool.work.R.layout.activity_root)
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(ru.myitschool.work.R.id.nav_host_fragment)
        navHostFragment!!.childFragmentManager.fragments[0]
        /*val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        if (navHostFragment != null) {
            val navController = navHostFragment.navController
            navController.graph = navController.createGraph(
                startDestination = LoginDestination
            ) {

                fragment<LoginFragment, LoginDestination>()
                fragment<QrScanFragment, QrScanDestination>()
            }
        }*/

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onSupportNavigateUp()
                }
            }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(ru.myitschool.work.R.id.nav_host_fragment)
        val popBackResult = if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        } else {
            false
        }
        return popBackResult || super.onSupportNavigateUp()
    }
}