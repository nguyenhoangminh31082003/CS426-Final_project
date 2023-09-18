package com.example.cs426_final_project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.example.cs426_final_project.ui.theme.CS426_final_projectTheme
import com.example.cs426_final_project.ui.theme.DarkGrey40
import com.example.cs426_final_project.ui.theme.Yellow


class SurveyDetailFragment : Fragment() {


    var questionTitle : String = "Survey"
    var answer : String = ""

    interface SurveyDetailFragmentListener {
        fun onDone(answer : String)
        fun onClose()
    }
    var surveyDetailFragmentListener : SurveyDetailFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                SurveyLayout(
                    questionTitle = questionTitle,
                    initAnswer = answer,
                    onDone = {
                        surveyDetailFragmentListener?.onDone(it)
                    },
                    onClose = {
                        surveyDetailFragmentListener?.onClose()
                    }
                )
            }
        }
    }

    // create new instance of fragment
    companion object {
        fun newInstance() = SurveyDetailFragment()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SurveyLayout(
    questionTitle: String = "Survey",
    onDone: (String) -> Unit = { },
    onClose: () -> Unit = { },
    initAnswer: String = ""
) {
    var answer by remember {
        mutableStateOf(initAnswer)
    }
    CS426_final_projectTheme(
        darkTheme = true,
        dynamicColor = false,
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize()
                // set background to have grey color but the alpha for the color is 0.8
                ,
            color = Color(0xD0000000)

            ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val (btnBack, tfAnswer, btnContinue, txtQuestion) = createRefs()

                Button(
                    onClick = {onClose()},
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
                    text = questionTitle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(txtQuestion) {
                            bottom.linkTo(tfAnswer.top, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                        },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    // set text size
                    fontSize = 20.sp,
                    // set color for text
                    color = Color.White
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tfAnswer) {
                            top.linkTo(parent.top, margin = 8.dp)
                            start.linkTo(parent.start, margin = 20.dp)
                            end.linkTo(parent.end, margin = 20.dp)
                            bottom.linkTo(parent.bottom, margin = 8.dp)
                        },
                    value = answer,
                    onValueChange = {
                         answer = it
                    },
                    label = {
                        Text(
                            text = "Type your answer here",
                            color = Yellow,
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Yellow,
                        unfocusedIndicatorColor = Color.White,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        cursorColor = Yellow,
                        textColor = Color.White,
                        placeholderColor = Color.White,
                        containerColor = Color.Transparent,
                        // set bottom line color
                        focusedLabelColor = Color.White,
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),

                )

                Button(
                    onClick = {
                        onDone(answer)
                    },
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
                        containerColor = if(answer.isNotEmpty()) Yellow else Color(0xFFDAA520).copy(alpha = 0.5f),
                        contentColor = Color.Black
                    ),
                    enabled = answer.isNotEmpty()
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
