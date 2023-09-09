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
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cs426_final_project.models.viewmodel.RegisterViewModel
import com.example.cs426_final_project.models.viewmodel.RegisterViewModelContract
import com.example.cs426_final_project.models.viewmodel.RegisterViewModelFactory
import com.example.cs426_final_project.ui.theme.CS426_final_projectTheme
import com.example.cs426_final_project.ui.theme.DarkGrey40
import com.example.cs426_final_project.ui.theme.EmailInput
import com.example.cs426_final_project.ui.theme.FullNameInput
import com.example.cs426_final_project.ui.theme.PasswordInput
import com.example.cs426_final_project.ui.theme.PhoneNumberInput
import com.example.cs426_final_project.ui.theme.Yellow
import com.example.cs426_final_project.utilities.EmailUtilityClass
import com.example.cs426_final_project.utilities.KeyboardUtilityClass

class RegisterFragment : Fragment() {
    lateinit var registerViewModel: RegisterViewModel

    interface RegisterContract {
        fun onSuccessRegister()
        fun onUnSuccessRegister()
    }

    var registerContract: RegisterContract? = null

    private val android.content.Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        registerViewModel = ViewModelProvider(this, RegisterViewModelFactory(
            object : RegisterViewModelContract {
                override fun onRegisterSuccess() {
                    registerContract?.onSuccessRegister()
                }
            },
            profilePreferences = requireActivity().getSharedPreferences(
                USER_PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        ))[RegisterViewModel::class.java]

        return ComposeView(requireContext()).apply {
            setContent {
                CS426_final_projectTheme {
                    RegisterLayout(
                        viewModel = registerViewModel,
                        onBackPressed = { onBackPressed() },
                        onContinueButtonClicked = {onContinueButtonClicked()},
                        onChangeEmail = {
                            onChangeEmail(it)
                        },
                        onChangePassword = {
                            onChangePassword(it)
                        },
                        onChangeFullName = {
                            onChangeFullName(it)
                        }
                    ) {
                        onChangePhoneNumber(it)
                    }
                }
            }
        }
    }

    private fun onChangePhoneNumber(phoneNumber: String) {
        registerViewModel.registerUiModel.phoneNumber = phoneNumber
    }

    private fun onChangeFullName(fullname: String) {
        registerViewModel.registerUiModel.fullName = fullname
    }

    private fun onChangePassword(password: String) {
        registerViewModel.registerUiModel.password = password
    }

    private fun onChangeEmail(email: String) {
        registerViewModel.registerUiModel.email = email
    }

    private fun onContinueButtonClicked() {
        registerViewModel.confirmRegisterInfo()
        registerContract?.onSuccessRegister()
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
    viewModel: RegisterViewModel? = null,
    onBackPressed: () -> Unit = {},
    onContinueButtonClicked: () -> Unit = {},
    onChangeEmail: (String) -> Unit = {},
    onChangePassword: (String) -> Unit = {},
    onChangeFullName: (String) -> Unit = {},
    onChangePhoneNumber: (String) -> Unit = {},

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
                val (txtRegister, btnBack, inpEmail, div, inpFullName, inpPassword, inpPassword2, inpPhoneNumber, btnContinue) = createRefs()
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
                    title = "password",
                    onChangePassword = {
                        onChangePassword(it)
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
                        onChangePassword(it)
                    }
                )

                FullNameInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)

                        .constrainAs(inpFullName) {
                            top.linkTo(inpPassword2.bottom, margin = 8.dp)
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
                        if(EmailUtilityClass().isValidEmail(viewModel?.registerUiModel?.email ?: "")) Yellow
                        else DarkGrey40,
                        contentColor = Color.Black
                    ),
                    enabled = EmailUtilityClass().isValidEmail(viewModel?.registerUiModel?.email ?: "")

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
    registerContract: RegisterFragment.RegisterContract
): Fragment {
    val fragment = RegisterFragment()
    fragment.registerContract = registerContract
    return fragment
}


