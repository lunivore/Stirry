package com.lunivore.stirry

import com.sun.javafx.stage.StageHelper
import javafx.application.Application
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Parent
import javafx.scene.control.Dialog
import javafx.scene.input.Clipboard
import javafx.scene.input.DataFormat
import javafx.stage.Modality
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit


class Stirry {

    companion object {
        val logger = LogManager.getLogger()
        var stage : Stage? = null

        fun initialize() {
            logger.debug("Initializing platform...")
            JFXPanel()
            logger.debug("...platform initialized")
        }

        fun runOnPlatform(runnable: () -> Unit) {
            logger.debug("Waiting for platform...")
            var queue = ArrayBlockingQueue<Boolean>(1)
            Platform.runLater({
                runnable()
                queue.put(true)
            })
            queue.poll(1L, TimeUnit.SECONDS)
            logger.debug("...platform idle")
        }

        fun rootNode(): Parent {
            val stagesWithRoots = StageHelper.getStages()
                    .filter {it.scene != null}
                    .filter {it.scene.root != null}

            if (stagesWithRoots.count() < 1) {
                throw IllegalStateException("No root node was found")
            }

            if (stagesWithRoots.count() > 1) {
                throw IllegalStateException("More than one root node was found")
            }

            return stagesWithRoots[0].scene.root
        }

        fun waitForPlatform() {
            runOnPlatform { }
        }

        fun launchApp(application: Application) {
            runOnPlatform {
                if (stage == null) { stage = Stage() }
                application.start(stage)
                Platform.setImplicitExit(false)
            }
        }

        fun stop() {
            runOnPlatform {
                val stageClosers : List<Runnable>
                        = StageHelper.getStages().map { it -> Runnable {it.close()} }
                stageClosers.forEach{ it.run() }
            }
        }

        fun  getClipboard(format: DataFormat): Any? {
            var result : Any? = null
            waitForPlatform()
            runOnPlatform {  result = Clipboard.getSystemClipboard().getContent(format)}
            return result
        }

        fun findModalDialog(): Parent {
            val modalStages = StageHelper.getStages().filtered {
                it.modality == Modality.APPLICATION_MODAL || it.modality == Modality.WINDOW_MODAL
            }

            if (modalStages.count() < 1) { throw IllegalStateException("Could not find modal dialog") }
            if (modalStages.count() > 1) { throw IllegalStateException("More than one modal dialog is present") }

            return modalStages[0].scene.root
        }
    }
}

