package com.lunivore.stirry

import javafx.event.Event
import javafx.scene.control.TextField
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class TextFieldTest : NodeTest() {

    @Test
    fun `should be able to enter text into a text box`() {
        // Given a text box that's going to set a flag when text is entered
        var textSet: Boolean = false
        val textField = Stirry.rootNode().stirFind<TextField>({ it is TextField })
        textField?.textProperty()?.addListener({ it -> textSet = true })

        // When we ask Stirry to set the text for us
        textField?.stirSetText("Hello!")

        // Then it should wait for the text to be set
        Assertions.assertTrue(textSet)
        Assertions.assertEquals("Hello!", textField?.text)
    }

    @Test
    fun `should be able to wait for text to change`() {
        // Given a text box that's going to set some text after some time
        var textSet: Boolean = false
        val textField = Stirry.rootNode().stirFind<TextField>({ it is TextField })
        val time = Date().time

        Thread({
            Thread.sleep(120)
            textField?.stirSetText("Hello!")
        }).start()

        // When we wait for the text to change
        textField?.stirUntil({ it.text == "Hello!"}, 2000, Event.ANY, {fail("Timed out!")})

        // Then we should be notified pretty quickly
        assertEquals("Hello!", textField?.text)
        assertTrue(Date().time - time < 150)
    }

    @Test
    fun `should timeout if waiting fails`() {
        // Given a text box that won't change
        val textField = Stirry.rootNode().stirFind<TextField>({ it is TextField })
        var timedOut = false

        // When we wait for the text to change
        textField?.stirUntil({ it.text == "Hello!"}, 200, Event.ANY, {timedOut = true})

        // Then it should time out
        assertTrue(timedOut)
    }
}
