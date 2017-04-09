package com.lunivore.stirry

import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.layout.VBox
import javafx.stage.Stage
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit
import java.util.function.Predicate
import java.util.stream.Collectors.toList
import javax.swing.tree.TreeNode
import kotlin.concurrent.thread


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
            return found as? T
        }

        private fun findFirst(children: List<Node>, predicate: (Node) -> Boolean): Node? {
            var found : Node? = null
            for (child in children) {
                println("Child is $child")

                if (predicate(child)) { return child }

                if (found == null && (child is Parent)) {
                    println("Child is parent")
                    found = findFirst(child.childrenUnmodifiable, predicate)
                }

                if (found != null) return found
            }
            return found
        }
    }
}