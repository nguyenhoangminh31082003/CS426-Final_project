package com.example.cs426_final_project.fragments.access

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.cs426_final_project.contracts.LoginContract
import com.example.cs426_final_project.ui.theme.CS426_final_projectTheme
import com.example.cs426_final_project.ui.theme.DarkGrey40
import com.example.cs426_final_project.ui.theme.EmailInput
import com.example.cs426_final_project.ui.theme.PasswordInput
import com.example.cs426_final_project.ui.theme.Yellow
import com.example.cs426_final_project.utilities.EmailUtilityClass
import com.example.cs426_final_project.utilities.KeyboardUtilityClass

class LoginComposeFragment : Fragment() {

    var loginContract: LoginContract? = null

    private val mutableStateUsername = mutableStateOf("")
    private val mutableStatePassword = mutableStateOf("")

    private fun fetchSavedValue(){
//        val (savedEmail: String, savedPassword: String) = getSavedEmailAndPassword()
        mutableStateUsername.value = loginContract?.email?.value ?: ""
//        mutableStatePassword.value = savedPassword
//        println("fetchSavedValue: $savedEmail, $savedPassword")
    }

    override fun onResume() {
        super.onResume()
        fetchSavedValue()
        println("onResume: ${mutableStateUsername.value}, ${mutableStatePassword.value}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchSavedValue()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fetchSavedValue()


        return ComposeView(requireContext()).apply {

            setContent {
                val currentEmail = remember { mutableStateUsername }
                val currentPassword = remember { mutableStatePassword }
                LoginLayout(

                    onBackPressed = {
                        KeyboardUtilityClass.hideKeyboard(requireActivity(), this)
                        // check null
                        println("onBackPressed: ${loginContract == null}")
                        loginContract?.returnToWelcome()
                    },
                    onContinueButtonClicked = {
                        //check null
                        println("onContinueButtonClicked: ${loginContract == null}")
                        loginContract?.onConfirm(
                            currentEmail.value,
                            currentPassword.value
                        )
                    },
                    savedEmail = mutableStateUsername.value,
                    savedPassword = mutableStatePassword.value,
                    currentEmail = currentEmail,
                    currentPassword = currentPassword
                )
            }
        }
    }

    private fun getSavedEmailAndPassword(): Pair<String, String> {
        val savedEmail: String
        val savedPassword: String

        // get shared preferences with name "login"
        val sharedPref = requireContext().getSharedPreferences(USER_PREFERENCES_NAME, MODE_PRIVATE)
        // get saved email and password
        savedEmail = sharedPref?.getString("email", "").toString()
        savedPassword = sharedPref?.getString("password", "").toString()
        return Pair(savedEmail, savedPassword)
    }
}
@Preview(showBackground = true)
@Composable
fun LoginLayout(
    onBackPressed: () -> Unit = {},
    onContinueButtonClicked: () -> Unit = {},
    savedEmail : String = "",
    savedPassword : String = "",
    currentEmail : MutableState<String> = remember { mutableStateOf(savedEmail) },
    currentPassword : MutableState<String> = remember { mutableStateOf(savedPassword) }
) {

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
                val (btnBack, inpEmail, inpPassword, btnContinue, div) = createRefs()
                Button(onClick = {
                    onBackPressed()
                },
                    modifier= Modifier
                        .size(48.dp)
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
                        },
                    title = "Email",
                    initValue = currentEmail.value,
                    onChangeEmail = {
                        currentEmail.value = it
                    },
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .constrainAs(div) {
                            top.linkTo(inpEmail.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                        },
                    color = Color.Transparent
                )

                PasswordInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(inpPassword) {
                            top.linkTo(div.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                        },
                    onChangePassword = {
                        currentPassword.value = it
                    },
                    title = "Password",
                )

                // create chain email and password
                createVerticalChain(
                    inpEmail,
                    div,
                    inpPassword,
                    chainStyle = ChainStyle.Packed
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
                            if(EmailUtilityClass().isValidEmail(currentEmail.value)) Yellow
                            else DarkGrey40,
                        contentColor = Color.Black
                    ),
                    enabled = EmailUtilityClass().isValidEmail(currentEmail.value)

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




fun newInstance(
    loginContract: LoginContract
): Fragment {
    return LoginComposeFragment().also {
        it.loginContract = loginContract
    }
}
