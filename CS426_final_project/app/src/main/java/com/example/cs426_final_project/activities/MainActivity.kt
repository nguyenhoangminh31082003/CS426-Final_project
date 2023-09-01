package com.example.cs426_final_project.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cs426_final_project.ui.theme.CS426_final_projectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CS426_final_projectTheme {
                // A surface container using the 'background' color from the theme
//                Column(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(
//                        text = "Hello World!",
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                    Text(
//                        text = "Nice to meet you!",
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
            }
        }
        //val intent = Intent(this, SignInActivity::class.java)
        val intent = Intent(this, FoodScanPage::class.java)
        startActivity(intent)
    }
}