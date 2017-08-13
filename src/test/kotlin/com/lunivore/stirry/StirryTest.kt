package com.lunivore.stirry

import com.lunivore.stirry.exampleapp.ExampleApp
import javafx.application.Platform
import javafx.scene.input.Clipboard
import javafx.scene.input.DataFormat
import javafx.scene.layout.GridPane
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class StirryTest : NodeTest() {

    @Test
    fun `should be able to grab an application node when there is only one`() {
        assertNotNull(Stirry.rootNode() as GridPane, "")
    }


    @Test
    fun shouldBeAbleToStopStirryAndStartAgain() {
        // Given the platform is initialized (which it is)

        // When we stop Stirry
        Stirry.stop()

        // Then we should be able to start it again
        Stirry.initialize()
        Stirry.launchApp(ExampleApp())
    }

    @Test
    fun shouldBeAbleToRecoverTextFromAClipboard() {
        // Given the platform is initialized (which it is)
        // and text has been put into the clipboard
        val content = mutableMapOf<DataFormat, Any>()
        content[DataFormat.PLAIN_TEXT] = "Clipped!"
        Platform.runLater { Clipboard.getSystemClipboard().setContent(content) }

        // When we ask Stirry to get the text
        val result = Stirry.getClipboard(DataFormat.PLAIN_TEXT)

        // Then it should be able to recover it for us
        assertEquals("Clipped!", result)
    }
}
