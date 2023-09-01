package com.example.cs426_final_project.notifications


import android.R
import android.graphics.fonts.FontFamily
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.cs426_final_project.ui.theme.CS426_final_projectTheme
import com.example.cs426_final_project.ui.theme.DarkGrey40
import com.example.cs426_final_project.ui.theme.LightGrey
import com.example.cs426_final_project.ui.theme.Yellow
import com.example.cs426_final_project.utilities.EmailUtilityClass
import com.google.type.Money
import java.time.format.TextStyle

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(value: String = "", setShowDialog: (Boolean) -> Unit = {}, setValue: (String) -> Unit = {}) {

    val txtFieldError = remember { mutableStateOf("") }
    val txtField = remember { mutableStateOf(value) }

    Dialog(
        onDismissRequest = { setShowDialog(false) },
    ) {
        CS426_final_projectTheme(
            dynamicColor = false,
            darkTheme = true,
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),

            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Change your email",
                                // set style

                                style = androidx.compose.ui.text.TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "",
                                tint = colorResource(android.R.color.darker_gray),
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                                    .clickable { setShowDialog(false) }
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    BorderStroke(
                                        width = 2.dp,
                                        color =
                                        if (txtFieldError.value.isEmpty()) Yellow
                                        else LightGrey
                                    ),
                                    shape = RoundedCornerShape(50)
                                ),
                            shape = RoundedCornerShape(50),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Email,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height(20.dp)
                                )
                            },
                            placeholder = { Text(text = "Enter your email") },
                            value = txtField.value,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            onValueChange = {
                                txtField.value = it.take(10)
                            })

                        Spacer(modifier = Modifier.height(20.dp))

                        Box(
                            modifier = Modifier
                                .padding(40.dp, 0.dp, 40.dp, 0.dp)
                                .background(
                                    // transparent
                                    color = Color.Transparent
                                ),

                            ) {
                            Button(
                                onClick = {
                                    if (txtField.value.isEmpty()) {
                                        txtFieldError.value = "Field can not be empty"
                                        return@Button
                                    }
                                    setValue(txtField.value)
                                    setShowDialog(false)
                                },
                                shape = CircleShape,
                                modifier = Modifier
                                    .padding(40.dp, 0.dp, 40.dp, 0.dp)
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                    if(EmailUtilityClass().isValidEmail(txtField.value)) Yellow
                                    else DarkGrey40,
                                    contentColor = Color.Black),

                                enabled = EmailUtilityClass().isValidEmail(txtField.value)
                            ) {
                                Text(
                                    text = "Done"
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}