package com.lunivore.stirry

import javafx.scene.control.Button
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ButtonTest : AbstractExampleApplicationTest() {

    @Test
    fun `should be able to click a button`() {
        // Given a button that's going to set a flag when it's clicked
        var clicked: Boolean = false
        val button = Stirry.findInRoot<Button>({ it.text == "Two" }).value

        button.onAction.also { clicked = true }

        // When we ask Stirry to click the button for us
        button.fireAndStir()

        // Then it should wait for the button to be clicked
        Assertions.assertTrue(clicked)
    }
}

