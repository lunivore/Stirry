package com.lunivore.stirry

import com.lunivore.stirry.exampleapp.ExampleApp
import javafx.application.Platform
import javafx.scene.input.Clipboard
import javafx.scene.input.DataFormat
import javafx.scene.layout.GridPane
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Alert
import javafx.scene.control.Button


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

    @Test
    fun `should be able to find modal dialog`() {
        // Given that the app's open (which it is)
        // and a modal dialog is open too
        Stirry.runOnPlatform {
            val alert = Alert(AlertType.INFORMATION)
            alert.title = "Dialog"
            alert.headerText = null
            alert.contentText = "Hello!"
            alert.showAndWait()
        }

        // When we find the modal dialog
        val dialog = Stirry.findModalDialog()

        // Then we should be able to close it
        dialog.stirFind<Button> { it is Button && it.text == "OK" }?.stirClick()

    }
}
