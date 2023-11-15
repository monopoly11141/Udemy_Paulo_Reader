package com.example.udemy_paulo_reader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.udemy_paulo_reader.screen.home.ReaderHomeScreen
import com.example.udemy_paulo_reader.screen.login.ReaderLoginScreen
import com.example.udemy_paulo_reader.screen.search.ReaderSearchScreen
import com.example.udemy_paulo_reader.screen.splash.ReaderSplashScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ReaderScreen.SplashScreen.name
    ) {
        composable(route = ReaderScreen.SplashScreen.name){
            ReaderSplashScreen(navController = navController)
        }

        composable(route = ReaderScreen.ReaderHomeScreen.name){
            ReaderHomeScreen(navController = navController)
        }

        composable(route = ReaderScreen.SearchScreen.name){
            ReaderSearchScreen(navController = navController)
        }

        composable(route = ReaderScreen.LoginScreen.name){
            ReaderLoginScreen(navController = navController)
        }
    }
}