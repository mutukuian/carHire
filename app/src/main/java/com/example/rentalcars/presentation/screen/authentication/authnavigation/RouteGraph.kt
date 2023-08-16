package com.example.rentalcars.presentation.screen.authentication.authnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rentalcars.presentation.screen.authentication.LogInScreen
import com.example.rentalcars.presentation.screen.authentication.RegisterScreen

@Composable
fun SetUpNavGraph(
    navController:NavHostController
){
    NavHost(navController = navController, startDestination = "register_screen"){
        composable(Screen.RegisterScreen.route){
            RegisterScreen(navController)
        }
        composable(Screen.LoginScreen.route){
            LogInScreen(navController)
        }
    }

}