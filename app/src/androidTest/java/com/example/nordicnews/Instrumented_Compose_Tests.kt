package com.example.nordicnews

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nordicnews.ui.theme.NordicNewsTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Instrumented_Compose_Tests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun fixedHeader_test() {
        composeTestRule.setContent{
            NordicNewsTheme {
                NordicNewsApp()
            }
        }

        composeTestRule.onNodeWithContentDescription("Fixed Header Async Image").assertExists(
            "No node with this text was found."
        )
    }
}