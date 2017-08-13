package com.lunivore.stirry

import javafx.scene.control.Button
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ParentTest() : NodeTest() {

    @Test
    fun `should be able to find children using predicates`() {
        var button = Stirry.rootNode().stirFind<Button>({
            it is Button && it.text == "One" })

        assertTrue(button?.text == "One")
    }

}
