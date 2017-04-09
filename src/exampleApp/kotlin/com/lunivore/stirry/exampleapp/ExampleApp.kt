package com.lunivore.stirry.exampleapp

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import javafx.stage.Stage

class ExampleApp : Application() {

    val gridPane = GridPane()

    override fun start(primaryStage: Stage) {
        gridPane.add(Button("One"), 0, 0)
        gridPane.add(Button("Two"), 0, 1)
        gridPane.add(TextField(), 0, 2)
        primaryStage.scene = Scene(gridPane)
        primaryStage.show()
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            launch(ExampleApp::class.java, *args)
        }
    }
}