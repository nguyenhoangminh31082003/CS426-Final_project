package com.example.cs426_final_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.cs426_final_project.contracts.SignInContract
import com.example.cs426_final_project.ui.theme.CS426_final_projectTheme
import com.example.cs426_final_project.ui.theme.DarkGrey40
import com.example.cs426_final_project.ui.theme.Yellow
import com.example.cs426_final_project.utilities.EmailUtilityClass

class EmailReceiverComposeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val signInContract: SignInContract
        try {
            signInContract = context as SignInContract
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement SignInContract")
        }

        return ComposeView(requireContext()).apply {
            setContent {
                EmailReceiverLayout(
                    onBackPressed = {
                        signInContract.returnToWelcome()
                    },
                    onContinueButtonClicked = {
                        signInContract.confirmEmail()
                    }
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun EmailReceiverLayout(
    onBackPressed: () -> Unit = {},
    onContinueButtonClicked: () -> Unit = {}
) {
    var currentEmail by remember { mutableStateOf("") }
    CS426_final_projectTheme(
        darkTheme = true,
        dynamicColor = false,
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize(),

        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val (btnBack, inpEmail, btnContinue) = createRefs()

                Button(onClick = {

                    onBackPressed()
                },
                    modifier= Modifier
                        .size(50.dp)
                        .constrainAs(btnBack) {
                            top.linkTo(parent.top, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                        },
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkGrey40,
                        )
                    ) {
                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "content description")

                }

                EmailInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(inpEmail) {
                            top.linkTo(parent.top, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                            bottom.linkTo(parent.bottom, margin = 8.dp)
                        },
                    onChangeEmail = {
                        currentEmail = it
                    }
                )

                Button(
                    onClick = onContinueButtonClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(1.dp, SolidColor(Color.Black), shape = CircleShape)
                        .constrainAs(btnContinue) {
                            bottom.linkTo(parent.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                        },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if(EmailUtilityClass().isValidEmail(currentEmail)) Yellow
                            else DarkGrey40,
                        contentColor = Color.Black
                    ),
                    enabled = EmailUtilityClass().isValidEmail(currentEmail)

                ){
                    Text(
                        text = "Continue",
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        // set text size
                        fontSize = 20.sp
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    onChangeEmail: (String) -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    Column(
        modifier = modifier
    ) {
        Text(
            text ="Enter your email",
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
            // set cursor color
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                // set border radius
                .border(3.dp, SolidColor(Color.Gray), shape = RoundedCornerShape(16.dp))

        )
    }
}



fun newInstance(): Fragment {
    return EmailReceiverComposeFragment()
}
