
package com.lunivore.stirry

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import org.apache.logging.log4j.LogManager
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit

fun Button.stirClick() {
    val logger = LogManager.getLogger()

    val queue = ArrayBlockingQueue<Boolean>(1)
    val handler : EventHandler<ActionEvent> = EventHandler { queue.put(true)}
    addEventHandler(ActionEvent.ACTION, handler)
    fire()
    Stirry.waitForPlatform()
    queue.poll(1L, TimeUnit.SECONDS)
    removeEventHandler(ActionEvent.ACTION, handler)
}

