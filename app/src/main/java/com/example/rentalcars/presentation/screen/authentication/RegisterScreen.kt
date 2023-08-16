package com.example.rentalcars.presentation.screen.authentication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.rentalcars.R
import com.example.rentalcars.presentation.screen.authentication.authnavigation.Screen
import com.example.rentalcars.presentation.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController:NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
){
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.carlogo),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.FillWidth
        )


        TextField(
            value = email,
            onValueChange = { email = it },
            label = {Text("Email")}
        )


        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(text = "Password")
            },
            visualTransformation = PasswordVisualTransformation()
        )


        Spacer(modifier = Modifier.height(15.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            onClick = {
                scope.launch {
                    viewModel.registerUser(email, password)
                }
            },
            shape = RoundedCornerShape(size = 8.dp)
        ) {
            Text(text = "Register")
        }


        Text(
            text = "Already have an account? LogIn",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            modifier = Modifier
                .clickable {
                    // Handle navigation to login screen
                    navController.navigate(Screen.LoginScreen.route)
                }
                .padding(vertical = 8.dp)
        )

    }

    val state = viewModel.registerState.collectAsState(initial = null )

    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        if (state.value?.isLoading == true){
            CircularProgressIndicator()
        }

    }


    Spacer(modifier = Modifier.height(24.dp))
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        LaunchedEffect(key1 = state.value?.isSuccess){
            scope.launch {
                if (state.value?.isSuccess?.isNotEmpty()==true){
                    val success = state.value?.isSuccess
                    Toast.makeText(context,"$success",Toast.LENGTH_SHORT).show()
                }
            }
        }

        LaunchedEffect(key1 = state.value?.isError){
            scope.launch {
                if (state.value?.isError?.isNotEmpty() == true){
                    val error = state.value?.isError
                    Toast.makeText(context,"$error",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}


