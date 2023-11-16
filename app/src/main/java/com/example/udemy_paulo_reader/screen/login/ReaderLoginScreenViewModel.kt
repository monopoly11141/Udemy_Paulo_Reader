package com.example.udemy_paulo_reader.screen.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow

class ReaderLoginScreenViewModel() : ViewModel() {
    //val loadingState = MutableStateFlow(LoadingState.IDLE)

    private val firebaseAuth: FirebaseAuth = Firebase.auth
}