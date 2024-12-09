package ru.myitschool.work.ui.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import ru.myitschool.work.R
import ru.myitschool.work.databinding.FragmentLoginBinding
import ru.myitschool.work.utils.collectWhenStarted
import java.io.IOException

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
                        binding.error.visibility = View.GONE
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
                    binding.error.visibility = View.GONE
                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url("http://10.0.2.2:9000/api/${binding.username.text}/auth")
                        .build()

                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            e.printStackTrace()
                        }

                        override fun onResponse(call: Call, response: Response) {
                            Log.d("biba",response.code().toString())
                            when(response.code()){
                                200 -> {
                                    //Успешно
                                    val sp =
                                        requireActivity().getSharedPreferences("hasLogIn", Context.MODE_PRIVATE)
                                    val e = sp.edit()
                                    e.putBoolean("hasLogIn", true)
                                    e.apply()
                                    val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                                    val navController = navHostFragment.navController
                                    navController.navigate(R.id.go_to_main_screen)

                                }
                                401 -> {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        binding.error.visibility = View.VISIBLE
                                    }
                                }
                            }
                        }
                    })

                }
            }
            }

    }
    /*val sp =
        requireActivity().getSharedPreferences("hasLogIn", Context.MODE_PRIVATE)
    val e = sp.edit()
    e.putBoolean("hasLogIn", true)
    e.apply()
    val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    val navController = navHostFragment.navController
    navController.navigate(R.id.go_to_main_screen)*/

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }
}