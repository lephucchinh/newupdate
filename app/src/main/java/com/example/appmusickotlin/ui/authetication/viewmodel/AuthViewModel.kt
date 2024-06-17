package com.example.appmusickotlin.ui.authetication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmusickotlin.model.User

class AuthViewModel : ViewModel(){
    private var _currentFragment = MutableLiveData<String>()
    var currentFragment: LiveData<String> = _currentFragment

    private val _username = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _email = MutableLiveData<String>()
    private val _rePassword = MutableLiveData<String>()
    val username : LiveData<String> get() = _username
    val email : LiveData<String> get() = _email
    val password : LiveData<String> get() = _password

    fun SignUp(username : String, email : String, password : String) {
        _username.value = username
        _email.value = email
        _password.value = password

        User.username = username
        User.email = email
        User.password = password
        User.rePassword = password
    }

    fun SignIn(username : String, password : String) : String {

        if (username != User.username || password != User.password) {
            return "Tài khoản hoặc mật khẩu sai"
        } else {
            return "Đăng nhập thành công"
        }
    }


    fun navigateToSignIn() {
        _currentFragment.value = "SignIn"
    }

    fun navigateToSignUp() {
        _currentFragment.value = "SignUp"

    }



}