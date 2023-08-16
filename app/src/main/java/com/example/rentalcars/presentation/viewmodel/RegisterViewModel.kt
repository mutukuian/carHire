package com.example.rentalcars.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentalcars.core.common.LogInRegisterState
import com.example.rentalcars.core.common.Resource
import com.example.rentalcars.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) :ViewModel(){

    private val _registerState = Channel<LogInRegisterState>()
    val registerState = _registerState.receiveAsFlow()

    fun registerUser(email:String,password:String) = viewModelScope.launch {
        repository.registerUser(email, password).collect{
            result ->
            when(result){
                is Resource.Success ->{
                    _registerState.send(LogInRegisterState(isSuccess = "Registration Successfully"))
                }
                is Resource.Loading ->{
                    _registerState.send(LogInRegisterState(isLoading = true))
                }
                is Resource.Error ->{
                    _registerState.send(LogInRegisterState(isError = result.message.toString()))
                }
            }
        }
    }

}