package com.example.cs426_final_project.fragments.access

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.remember
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
import androidx.lifecycle.ViewModelProvider
import com.example.cs426_final_project.models.data.RegisterDataModel
import com.example.cs426_final_project.models.viewmodel.RegisterViewModel
import com.example.cs426_final_project.models.viewmodel.RegisterViewModelContract
import com.example.cs426_final_project.models.viewmodel.RegisterViewModelFactory
import com.example.cs426_final_project.ui.theme.CS426_final_projectTheme
import com.example.cs426_final_project.ui.theme.DarkGrey40
import com.example.cs426_final_project.ui.theme.EmailInput
import com.example.cs426_final_project.ui.theme.FullNameInput
import com.example.cs426_final_project.ui.theme.PasswordInput
import com.example.cs426_final_project.ui.theme.PhoneNumberInput
import com.example.cs426_final_project.ui.theme.UserNameInput
import com.example.cs426_final_project.ui.theme.Yellow
import com.example.cs426_final_project.utilities.EmailUtilityClass
import com.example.cs426_final_project.utilities.KeyboardUtilityClass

class RegisterFragment : Fragment() {

    interface RegisterContract {
        fun onSuccessRegister(email: String)
        fun onUnSuccessRegister()
    }

    var registerContract: RegisterContract? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                CS426_final_projectTheme {
                    val registerViewModel = lazy {
                        ViewModelProvider(
                            this@RegisterFragment,
                            RegisterViewModelFactory(
                                object : RegisterViewModelContract {
                                    override fun onRegisterSuccess(email: String) {
                                        registerContract?.onSuccessRegister(email)
                                    }
                                },
                                requireActivity().getSharedPreferences(
                                    USER_PREFERENCES_NAME,
                                    Context.MODE_PRIVATE
                                )
                            )
                        )[RegisterViewModel::class.java]
                    }

                    val registerUiModel = remember {
                        registerViewModel.value.registerUiModel
                    }


                    RegisterLayout(
                        registerUiModel = registerUiModel,
                        isValid = {
                            EmailUtilityClass().isValidEmail(registerUiModel.value.email)
//                                  true
//                                  EmailUtilityClass().isValidEmail(registerUiModel.email)
                        },
                        onBackPressed = { onBackPressed() },
                        onContinueButtonClicked = {
                            registerViewModel.value.confirmRegisterInfo(requireContext())
                            registerContract?.onSuccessRegister("")
                                                  },
                        onChangeEmail = {
//                            registerUiModel.value = registerUiModel.value.copy(email = it)
                            registerUiModel.value = registerUiModel.value.copy(email = it)

                        },
                        onChangePassword = {
                            registerUiModel.value = registerUiModel.value.copy(password = it)
                        },
                        onChangeFullName = {
                            registerUiModel.value = registerUiModel.value.copy(fullName = it)

                        },
                        onChangePhoneNumber = {
                            registerUiModel.value = registerUiModel.value.copy(phoneNumber = it)
                        },
                        onChangeUserName = {
                            registerUiModel.value = registerUiModel.value.copy(username = it)
                        },
                        onChangePassword2 = {
                            registerUiModel.value = registerUiModel.value.copy(confirmPassword = it)
                        },
                    )
                }
            }
        }
    }

    private fun onBackPressed() {
//        requireActivity().onBackPressedDispatcher.onBackPressed()
        KeyboardUtilityClass.hideKeyboard(requireContext(), requireView())
        registerContract?.onUnSuccessRegister()
    }

}

@Preview(showBackground = true)
@Composable
fun RegisterLayout(
    registerUiModel : MutableState<RegisterDataModel>? = null,
    isValid: () -> Boolean = { true },
    onBackPressed: () -> Unit = {},
    onContinueButtonClicked: () -> Unit = {},
    onChangeEmail: (String) -> Unit = {},
    onChangePassword: (String) -> Unit = {},
    onChangeFullName: (String) -> Unit = {},
    onChangePhoneNumber: (String) -> Unit = {},
    onChangeUserName: (String) -> Unit = {},
    onChangePassword2: (String) -> Unit = {},
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
                    .verticalScroll(rememberScrollState())
            ) {
                val (
                    txtRegister, btnBack,
                    inpEmail, div,
                    inpFullName, inpPassword,
                    inpPassword2, inpPhoneNumber,
                    inpUsername, btnContinue) = createRefs()
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

                Text(
                    text = "Register an account",
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(txtRegister) {
                            top.linkTo(btnBack.bottom, margin = 8.dp)
                            start.linkTo(btnBack.end, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                        },
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                        .constrainAs(div) {
                            top.linkTo(txtRegister.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                        },
                    color = Color.Transparent,
                    thickness = 32.dp
                )

                EmailInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        // margin bottom 8dp
                        .padding(bottom = 32.dp)
                        .constrainAs(inpEmail) {
                            top.linkTo(div.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                        },
                    onChangeEmail = {
                        onChangeEmail(it)
//                        if (registerUiModel != null) {
//                            registerUiModel.value = registerUiModel.value.copy(email = it)
//                        }
                    }
                )

                PasswordInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)

                        .constrainAs(inpPassword) {
                            top.linkTo(inpEmail.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                        },
                    title = "Password",
                    onChangePassword = {
                        onChangePassword(it)
//                        registerUiModel.value.password = it
                    }
                )

                PasswordInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)

                        .constrainAs(inpPassword2) {
                            top.linkTo(inpPassword.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                        },
                    title = "Confirmed password",
                    onChangePassword = {
                        onChangePassword2(it)
                    }
                )

                UserNameInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                        .constrainAs(inpUsername) {
                            top.linkTo(inpPassword2.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                        },
                    onChangeFullName = {
                        onChangeUserName(it)
                    }
                )

                FullNameInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)

                        .constrainAs(inpFullName) {
                            top.linkTo(inpUsername.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                        },
                    onChangeFullName = {
                        onChangeFullName(it)
                    }
                )

                PhoneNumberInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)

                        .constrainAs(inpPhoneNumber) {
                            top.linkTo(inpFullName.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                            bottom.linkTo(btnContinue.top, margin = 8.dp)
                        },
                    onChangePhoneNumber = {
                        onChangePhoneNumber(it)
                    }
                )


                // create chain for email, password, fullname, phone number
//                createVerticalChain(
//                    inpEmail,
//                    inpPassword,
//                    inpPassword2,
//                    inpFullName,
//                    inpPhoneNumber,
//                    chainStyle = ChainStyle.Packed(0.5f),
//                )

                if (registerUiModel != null) {
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
                            if(isValid())
                                Yellow
                            else DarkGrey40,
                            contentColor = Color.Black
                        ),
                        enabled =  isValid()

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
}

fun newInstance(
    registerContract: RegisterFragment.RegisterContract
): Fragment {
    val fragment = RegisterFragment()
    fragment.registerContract = registerContract
    return fragment
}


