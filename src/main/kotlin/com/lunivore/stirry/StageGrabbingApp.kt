package com.lunivore.stirry

import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kotlinx.coroutines.experimental.*
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit

class StageGrabbingApp : Application() {

    companion object CapturedStage {
        private val queue = ArrayBlockingQueue<Stage>(1)
        private var stage: Stage? = null

        fun capturedStage() : Stage? {
            stage = queue.poll(10L, TimeUnit.SECONDS)
            return stage
        }
    }

    override fun start(primaryStage: Stage) {
        val scene = Scene(VBox(), 100.0, 50.0)
        primaryStage.title = "Stirry"
        primaryStage.scene = scene
        primaryStage.show()
        primaryStage.toFront()
        addToQueue(primaryStage)
    }

    private fun  addToQueue(stage: Stage){
        queue.put(stage)
    }

}