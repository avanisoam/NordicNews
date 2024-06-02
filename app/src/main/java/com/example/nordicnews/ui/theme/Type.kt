package com.example.nordicnews.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.nordicnews.R

// Set of Material typography styles to start with

val Roboto = FontFamily(
    Font(R.font.roboto_regular),
    Font(R.font.roboto_bold),
    Font(R.font.roboto_medium)
)

val Typography = Typography(
    headlineLarge = robotoTextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineMedium = robotoTextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    headlineSmall = robotoTextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyLarge = robotoTextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 24.sp
    ),
    // TODO: Check if caption is required instead of label as per figma requirement
    labelSmall = robotoTextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),

)

fun robotoTextStyle(
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit = TextUnit.Unspecified
): TextStyle = TextStyle(
    fontFamily = Roboto,
    letterSpacing = 0.sp,
    fontWeight = fontWeight,
    fontSize = fontSize,
    lineHeight = lineHeight
)

/*
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )


    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)    */