package com.stephenwoerner.sabrinafoodapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.stephenwoerner.sabrinafoodapp.ui.RestaurantListUI
import com.stephenwoerner.sabrinafoodapp.ui.theme.SabrinaFoodAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SabrinaFoodAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RestaurantListUI(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}