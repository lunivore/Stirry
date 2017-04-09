package com.lunivore.stirry

import com.lunivore.stirry.Stirry.Companion.find
import com.lunivore.stirry.exampleapp.ExampleApp
import javafx.scene.control.Button
import javafx.scene.layout.GridPane
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
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
        val button = find<Button>({b -> b is Button && b.text == "Two"})
        assertTrue(button?.text == "Two")
    }
}
