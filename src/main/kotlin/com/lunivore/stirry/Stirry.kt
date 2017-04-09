package com.lunivore.stirry

import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.control.TextInputControl
import javafx.stage.Stage
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit


class Stirry {

    companion object {

        private var stage: Stage? = null

        fun initialize() {
            if (stage == null) {
                Thread({ Application.launch(StageGrabbingApp::class.java) }).start()
            }
            stage = StageGrabbingApp.capturedStage()
        }

        private fun waitForPlatform() {
            val queue = ArrayBlockingQueue<Boolean>(1)
            Platform.runLater({ queue.put(true) })
            queue.poll(10L, TimeUnit.SECONDS)
        }

        fun rootNode(): Node? {
            return stage?.scene?.root
        }


        fun startApp(app: Application) {
            if (stage == null) throw IllegalStateException("Stirry must be initialized!")
            Platform.runLater({ app.start(stage) })
            waitForPlatform()
        }

        fun <T : Node> find(predicate: (Node) -> Boolean): T? {
            val parent = rootNode() as? Parent ?:
                    throw IllegalStateException("Top node is not a parent.")

            val found : Node? = findFirst(
                    parent.childrenUnmodifiable,
                    predicate)
            if (found == null) { throw Exception("Node not found") }
            return found as? T
        }

        private fun findFirst(children: List<Node>, predicate: (Node) -> Boolean): Node? {
            var found : Node? = null
            for (child in children) {

                if (predicate(child)) { return child }

                if (found == null && (child is Parent)) {
                    found = findFirst(child.childrenUnmodifiable, predicate)
                }
                if (found != null) return found
            }
            return found
        }

        fun buttonClick(predicate : (Button) -> Boolean) {
            var button = find<Button>({ it is Button && predicate(it) })
            if (button == null) { throw IllegalStateException("The button is missing or not available") }

            val queue = ArrayBlockingQueue<Boolean>(1)
            button.onAction.also { queue.put(true) }
            button?.fire()
            waitForPlatform()
            queue.poll(1L, TimeUnit.SECONDS)
        }

        fun  setText(predicate: (TextInputControl) -> Boolean, desiredText: String) {
            var textField = find<TextField>({it is TextField && predicate(it)})
            if (textField == null) { throw IllegalStateException("The textField is missing or not available") }

            val queue = ArrayBlockingQueue<Boolean>(1)
            textField.onAction.also { queue.put(true)}
            textField?.text = desiredText
            waitForPlatform()
            queue.poll(1L, TimeUnit.SECONDS)
        }
    }
}