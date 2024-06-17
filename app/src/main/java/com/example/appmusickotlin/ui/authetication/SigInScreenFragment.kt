package com.example.appmusickotlin.ui.authetication

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appmusickotlin.R
import com.example.appmusickotlin.databinding.FragmentSigInScreenBinding
import com.example.appmusickotlin.model.User
import com.example.appmusickotlin.ui.authetication.viewmodel.AuthViewModel
import com.example.appmusickotlin.ui.home.HomeScreenActivity

class SigInScreenFragment : Fragment() {
    private var _binding: FragmentSigInScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSigInScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)

        // Quan sát LiveData từ AuthViewModel để cập nhật EditText
        binding.edtEmail.setText(authViewModel.username.value)
        binding.edtPassword.setText(authViewModel.password.value)
        binding.btnLogin.setOnClickListener {


            val text = authViewModel.SignIn(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString()
            )

            if (text == "Đăng nhập thành công") {
                val intent = Intent(requireContext(), HomeScreenActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        showPassword()
        backSignUp()
    }

    private fun backSignUp(){
        binding.txtSignup.setOnClickListener {
            authViewModel.navigateToSignUp()
        }
    }

    private fun showPassword() {
        binding.imgShowPassword.setOnClickListener {
            if (binding.edtPassword.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                binding.edtPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.imgShowPassword.setImageResource(R.drawable.ic_eyeclose)
            } else {
                binding.edtPassword.inputType =
                    (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                binding.imgShowPassword.setImageResource(R.drawable.ic_eye)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}