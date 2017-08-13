package com.lunivore.stirry

import javafx.scene.control.Button
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ButtonTest : NodeTest() {

    @Test
    fun shouldBeAbleToClickAButton() {
        // Given a button that's going to set a flag when it's clicked
        var clicked: Boolean = false
        val button = Stirry.rootNode().stirFind<Button>({ it is Button && it.text == "Two" })

        button?.onAction.also { clicked = true }

        // When we ask Stirry to click the button for us
        button?.stirClick()

        // Then it should wait for the button to be clicked
        Assertions.assertTrue(clicked)
    }
}

