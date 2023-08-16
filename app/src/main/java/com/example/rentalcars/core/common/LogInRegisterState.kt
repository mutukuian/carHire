package com.example.rentalcars.core.common

data class LogInRegisterState (
    val isLoading:Boolean = false,
    val isSuccess:String = "",
    val isError:String= ""
)