package com.lunivore.stirry

import javafx.scene.control.TextField
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class TextFieldTest : AbstractExampleApplicationTest() {

    @Test
    fun `should be able to enter text into a text box`() {
        // Given a text box that's going to set a flag when text is entered
        var textSet: Boolean = false
        val textField = Stirry.findInRoot<TextField>({ true }).value
        textField.textProperty()?.addListener({ it -> textSet = true })

        // When we ask Stirry to set the text for us
        textField.setTextAndStir("Hello!")

        // Then it should wait for the text to be set
        Assertions.assertTrue(textSet)
        Assertions.assertEquals("Hello!", textField.text)
    }

    @Test
    fun `should be able to wait for text to change`() {
        // Given a text box that's going to set some text after some time
        var textSet: Boolean = false
        val textField = Stirry.findInRoot<TextField>({ it is TextField }).value
        val time = Date().time

        Thread({
            Thread.sleep(120)
            textField.setTextAndStir("Hello!")
        }).start()

        // When we wait for the text to change
        val result = textField.waitForText({ if (it.text == "Hello!") it.text else null}, 2000)

        // Then we should be notified pretty quickly
        assertEquals("Hello!", result.value)

        // Way, way quicker than timing out and checking again, which is what we have to do if we
        // can't listen to text property changes.
        assertTrue(Date().time - time < 150)
    }

    @Test
    fun `should timeout if waiting fails`() {
        // Given a text box that won't change
        val textField = Stirry.findInRoot<TextField>({ true }).value

        // When we wait for the text to change
        val result = textField.waitForText({ if (it.text == "Hello!") it.text else null}, 2000)

        // Then it should time out
        assertFalse(result.succeeded)
    }
}
