package ru.myitschool.work.ui.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.myitschool.work.R
import ru.myitschool.work.databinding.FragmentLoginBinding
import ru.myitschool.work.ui.main_screen.MainScreenFragment
import ru.myitschool.work.ui.qr.scan.QrScanFragment

import ru.myitschool.work.utils.collectWhenStarted

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        subscribe()
    }

    private fun subscribe() {
        viewModel.state.collectWhenStarted(this) { state ->
            val sp = requireActivity().getSharedPreferences("hasLogIn", Context.MODE_PRIVATE)
            val hasLogIn = sp.getBoolean("hasLogIn", false)

            if (hasLogIn) {
                val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                val navController = navHostFragment.navController

// Navigate using the IDs you defined in your Nav Graph
                navController.navigate(R.id.go_to_main_screen)
            } else {
                binding.error.visibility = View.GONE
                binding.login.isEnabled = false
                val numbers = "0123456789"
                binding.username.addTextChangedListener(object : TextWatcher {
                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val str = s.toString()
                        val p = Regex("[^A-Za-z0-9 ]", RegexOption.IGNORE_CASE)
                        val b = p.containsMatchIn(str)

                        if (str.isNotEmpty() && str.length >= 3 && !numbers.contains(str[0]) && !b) {
                            binding.login.isEnabled = true
                        } else {
                            binding.login.isEnabled = false
                        }

                    }
                })
                binding.login.setOnClickListener {
                    val sp =
                        requireActivity().getSharedPreferences("hasLogIn", Context.MODE_PRIVATE)
                    val e = sp.edit()
                    e.putBoolean("hasLogIn", true)
                    e.apply()


                    val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                    val navController = navHostFragment.navController

// Navigate using the IDs you defined in your Nav Graph
                    navController.navigate(R.id.go_to_main_screen)



                    //ниже код, который меняет фрагмент
                    /*val fragment = MainScreenFragment()
                    val fm = requireActivity().supportFragmentManager
                    val ft = fm.beginTransaction()
                    //ft.replace(R.id.fragment_login, fragment)
                    ft.commit()*/
                }
            }
            }

    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }
}