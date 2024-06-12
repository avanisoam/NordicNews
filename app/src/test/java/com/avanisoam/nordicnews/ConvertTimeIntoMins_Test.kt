package com.avanisoam.nordicnews

import com.avanisoam.nordicnews.ui.shared.getOffset
import org.junit.Assert
import org.junit.Test

class ConvertTimeIntoMins_Test {

    @Test
    fun getOffset_test() {
        // Arrange
        val input = "2024-05-01T15:06:39Z"

        // Act
        val offSetInMinutes = getOffset(input)

        // Assert
        Assert.assertEquals("27 days", offSetInMinutes)
    }
}