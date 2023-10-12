package com.example.rentalcars.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentalcars.core.common.LogInRegisterState
import com.example.rentalcars.core.common.Resource
import com.example.rentalcars.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) :ViewModel(){


    private val _logInState = Channel<LogInRegisterState>()
    val  logInState = _logInState.receiveAsFlow()


    fun logInUser(email:String,password:String) = viewModelScope.launch {
        repository.loginUser(email, password).collect{
                result ->
            when(result){
                is Resource.Success ->{
                    _logInState.send(LogInRegisterState(isSuccess = "LogIn Successfully"))
                }
                is Resource.Loading ->{
                    _logInState.send(LogInRegisterState(isLoading = true))
                }
                is Resource.Error ->{
                    _logInState.send(LogInRegisterState(isError = result.message.toString()))
                }
            }
        }
    }

}