package com.example.cs426_final_project.ui.theme

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    onChangeEmail: (String) -> Unit = {},
    title: String = "Enter your email",
    initValue : String = ""
) {
    var email by remember { mutableStateOf(initValue) }
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
            ,

            color = Color.White,
            fontWeight = FontWeight.Bold

        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email,
            onValueChange = {
                email = it
                onChangeEmail(it)
            },
            // disable bottom line
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                cursorColor = Yellow,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            // set cursor color
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                // set border radius
                .border(3.dp, SolidColor(Color.Gray), shape = RoundedCornerShape(16.dp))

        )
    }
}