package com.lunivore.stirry.exampleapp

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.stage.Stage

class ExampleApp : Application() {

    val gridPane = GridPane()
    val tabPane = TabPane()

    override fun start(primaryStage: Stage) {

        val tab1 = Tab()
        val tab2 = Tab()

        gridPane.add(Button("One"), 0, 0)
        gridPane.add(Button("Two"), 0, 1)
        gridPane.add(TextField(), 0, 2)
        gridPane.add(DatePicker(), 0, 3)
        gridPane.add(CheckBox(), 0, 4)


        tab1.setContent(gridPane)
        tab2.setContent(GridPane())
        tabPane.tabs.addAll(tab1, tab2)

        primaryStage.scene = Scene(tabPane)
        primaryStage.show()
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            launch(ExampleApp::class.java, *args)
        }
    }
}