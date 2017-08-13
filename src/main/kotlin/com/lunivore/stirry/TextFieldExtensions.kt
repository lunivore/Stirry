package com.lunivore.stirry

import javafx.application.Platform
import javafx.beans.value.ChangeListener
import javafx.event.Event
import javafx.event.EventType
import javafx.scene.control.TextField
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit

fun TextField.stirSetText(desiredText: String) {
    val queue = ArrayBlockingQueue<Boolean>(1)
    var listener : ChangeListener<String> = ChangeListener { s, t, u -> queue.put(true)}
    textProperty().addListener(listener)
    Platform.runLater({ text = desiredText })
    Stirry.waitForPlatform()
    queue.poll(1L, TimeUnit.SECONDS)
    textProperty().removeListener(listener)
}

fun TextField.stirUntil(
        predicate: (TextField) -> Boolean,
        timeoutInMilliseconds: Long = 100,
        event: EventType<Event> = Event.ANY,
        failureHandler: () -> Unit) {

    val queue = ArrayBlockingQueue<Boolean>(1)
    var listener : ChangeListener<String> = ChangeListener { s, t, u -> if (predicate(this)) queue.put(true)}
    textProperty().addListener(listener)
    val result = queue.poll(timeoutInMilliseconds, TimeUnit.MILLISECONDS)
    textProperty().removeListener(listener)
    if (result == null) { failureHandler() }
}