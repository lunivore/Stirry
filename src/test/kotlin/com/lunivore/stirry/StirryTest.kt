package com.lunivore.stirry

import com.lunivore.stirry.Stirry.Companion.find
import com.lunivore.stirry.exampleapp.ExampleApp
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class StirryTest {

    companion object {
        @JvmStatic
        @BeforeAll
        fun initializeStirry() {
            Stirry.initialize()
            Stirry.startApp(ExampleApp())
        }
    }

    @Test
    fun shouldBeAbleToGrabAnApplicationNode() {
        assertNotNull(Stirry.rootNode() as GridPane, "")
    }

    @Test
    fun shouldBeAbleToFindAComponentWithPredicate() {
        val button = find<Button>({b -> b is Button && b.text == "One"})
        assertTrue(button?.text == "One")
    }

    @Test
    fun shouldBeAbleToClickAButton() {

        // Given a button that's going to set a flag when it's clicked
        var clicked : Boolean = false
        val button = find<Button>({it is Button && it.text == "Two"})
        button?.onAction.also { clicked = true }

        // When we ask Stirry to click the button for us
        Stirry.buttonClick({it.text == "Two"})

        // Then it should wait for the button to be clicked
        assertTrue(clicked)
    }

    @Test
    fun shouldBeAbleToEnterTextIntoATextBox() {
        // Given a text box that's going to set a flag when text is entered
        var textSet : Boolean = false
        val textField = find<TextField>({it is TextField})
        textField?.onAction.also { textSet = true }

        // When we ask Stirry to set the text for us
        Stirry.setText({it is TextField}, "Hello!")

        // Then it should wait for the text to be set
        assertTrue(textSet)
        assertEquals("Hello!", textField?.text)
    }
}
