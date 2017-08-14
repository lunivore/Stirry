package com.lunivore.stirry

import javafx.event.Event
import javafx.scene.control.TextField
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class NodeTest : AbstractExampleApplicationTest() {

    @Test
    fun `should be able to wait for something to happen to a node and return the result`() {
        // Given a text box that's going to set some text after some time
        var textSet: Boolean = false
        val textField = Stirry.findInRoot<TextField>({ it is TextField }).value
        val time = Date().time

        Thread({
            Thread.sleep(50)
            textField.setTextAndStir("Hello!")
        }).start()

        // When we wait for the text to change
        val result = textField.waitFor<TextField, String, Event>(
                {if (it.text == "Hello!") it.text else null},
                1000,
                Event.ANY)

        // Then we should be notified pretty quickly
        Assertions.assertTrue(result.succeeded)
        Assertions.assertEquals("Hello!", result.value)
        Assertions.assertTrue(Date().time - time < 1500)
    }

    @Test
    fun `should timeout if waiting fails`() {
        // Given a text box that won't change
        val textField = Stirry.findInRoot<TextField>({ it is TextField }).value

        // When we wait for the text to change
        val result = textField.waitFor<TextField, Boolean, Event>(
                {if (it.text == "Hello!") true else null}, 1000, Event.ANY)

        // Then it should time out
        Assertions.assertFalse(result.succeeded)
    }
}