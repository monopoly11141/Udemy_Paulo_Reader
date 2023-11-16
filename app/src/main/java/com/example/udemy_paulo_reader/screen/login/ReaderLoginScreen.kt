package com.example.udemy_paulo_reader.screen.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.udemy_paulo_reader.R
import com.example.udemy_paulo_reader.component.EmailInput
import com.example.udemy_paulo_reader.component.ReaderLogo

@Composable
fun ReaderLoginScreen(navController: NavController) {

    var isShowLoginForm by rememberSaveable { mutableStateOf(true) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ReaderLogo()

            if (isShowLoginForm) {
                UserForm(
                    isLoading = false,
                    isCreateAccount = false
                ) { email, password ->
                    Log.d(TAG, "ReaderLoginScreen: $email and $password")
                }
            } else {
                UserForm(
                    isLoading = false,
                    isCreateAccount = true
                ) { email, password ->
                    Log.d(TAG, "ReaderLoginScreen: $email and $password")
                }
            }
        }

        Spacer(
            modifier = Modifier
                .height(15.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(15.dp)
        ) {
            val text = if (isShowLoginForm) "New User? Sign up" else "Login"

            Text(
                text = text,
                modifier = Modifier
                    .clickable {
                        isShowLoginForm = !isShowLoginForm
                    }
                    .padding(start = 5.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary

            )

        }

    }

}

@Preview
@Composable
fun UserForm(
    isLoading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, password -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val isPasswordVisible = rememberSaveable { mutableStateOf(false) }

    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val isValidEmailAndPassword = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(250.dp)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        if(isCreateAccount) {
            Text(
                text = stringResource(id = R.string.create_account),
                modifier = Modifier
                    .padding(4.dp)
            )
        }else {
            Text("")
        }
        EmailInput(
            emailState = email,
            isEnabled = !isLoading,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            }
        )

        PasswordInput(
            modifier = Modifier
                .focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "password",
            isEnabled = !isLoading,
            isPasswordVisible = isPasswordVisible,
            onAction = KeyboardActions {
                if (!isValidEmailAndPassword) {
                    return@KeyboardActions
                } else {
                    onDone(email.value.trim(), password.value.trim())
                }
            }
        )

        SubmitButton(
            textId = if (isCreateAccount) "Create Account" else "Login",
            isLoading = isLoading,
            isValidEmailAndPassword = isValidEmailAndPassword
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

@Composable
fun SubmitButton(
    textId: String,
    isLoading: Boolean,
    isValidEmailAndPassword: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !isLoading && isValidEmailAndPassword,
        shape = CircleShape,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(25.dp)
            )
        } else {
            Text(
                text = textId,
                modifier = Modifier
                    .padding(5.dp)
            )
        }
    }
}

@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    isEnabled: Boolean,
    isPasswordVisible: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
) {
    val visualTransformation = if (isPasswordVisible.value) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = { Text(text = labelId) },
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
        ),
        modifier = modifier
            .padding(
                bottom = 10.dp,
                start = 10.dp,
                end = 10.dp
            )
            .fillMaxWidth(),
        enabled = isEnabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        trailingIcon = {
            PasswordVisibility(isPasswordVisible = isPasswordVisible)
        }
    )
}

@Composable
fun PasswordVisibility(isPasswordVisible: MutableState<Boolean>) {
    val isPasswordVisibleState = isPasswordVisible.value
    IconButton(
        onClick = { isPasswordVisible.value = !isPasswordVisibleState }
    ) {
        Icons.Default.Close
    }
}
