package com.example.hyperabhinav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.hyperabhinav.ui.navigation.NavHostController
import com.example.hyperabhinav.ui.theme.HyperAbhinavTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            HyperAbhinavTheme {
//                Scaffold(
//                    contentWindowInsets = WindowInsets.safeDrawing,
//                    modifier = Modifier.fillMaxSize()
//                ) { innerPadding ->
//                    Surface(
//                        color = Color.Transparent,
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding),
//                    ) {
//                    }
//                }

                NavHostController(intent)
            }
        }
    }
}