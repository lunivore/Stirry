package com.lunivore.stirry

import javafx.scene.control.CheckBox
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CheckBoxTest : AbstractExampleApplicationTest() {

    @Test
    fun `should be able to select and deselect a checkbox`() {
        // Given the example app is running (which it is)

        // When I select the checkbox
        val checkbox = Stirry.findInRoot<CheckBox>({true}).value
        checkbox.checkAndStir()

        // Then it should be checked
        assertTrue(checkbox.isSelected)

        // When I clear the checkbox
        checkbox.clearAndStir()

        // Then it should not be checked
        assertFalse(checkbox.isSelected)
    }
}
