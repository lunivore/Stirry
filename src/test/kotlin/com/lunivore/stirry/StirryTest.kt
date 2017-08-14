package com.lunivore.stirry

import com.lunivore.stirry.exampleapp.ExampleApp
import javafx.application.Platform
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.input.Clipboard
import javafx.scene.input.DataFormat
import javafx.scene.layout.GridPane
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class StirryTest : AbstractExampleApplicationTest() {

    @Test
    fun `should be able to grab an application node when there is only one`() {
        assertNotNull(Stirry.findRoot() as GridPane, "")
    }


    @Test
    fun `should be able to stop Stirry and start it again`() {
        // Given the platform is initialized (which it is)

        // When we stop Stirry
        Stirry.stop()

        // Then we should be able to start it again
        Stirry.initialize()
        Stirry.launchApp(ExampleApp())
    }

    @Test
    fun `should be able to recover text from a clipboard`() {
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
        dialog.find<Button>{ it is Button && it.text == "OK" }.value.fireAndStir()

    }

    @Test
    fun `should be able to find things in the root node`() {
        // Given the app's running (which it is)

        // When we ask for an existing node
        val result = Stirry.findInRoot<Button> {it.text == "Two"}

        // Then we should find it.
        assertTrue(result.succeeded)
        assertEquals("Two", result.value.text)
    }

    @Test
    fun `should be able to find things in modal dialogs`() {
        // Given the app's running (which it is)
        // and a modal dialog is open too
        Stirry.runOnPlatform {
            val alert = Alert(AlertType.INFORMATION)
            alert.title = "Dialog"
            alert.headerText = null
            alert.contentText = "Hello!"
            alert.showAndWait()
        }

        // When we look for the button
        val result = Stirry.findInModalDialog<Button> {it.text == "OK"}

        // Then we should find it.
        assertTrue(result.succeeded)
        assertEquals("OK", result.value.text)
    }
}
