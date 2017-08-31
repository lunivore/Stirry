package com.lunivore.stirry

import javafx.beans.value.ChangeListener
import javafx.scene.control.CheckBox
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit

fun CheckBox.checkAndStir() {
    setAndStir(true)
}

fun CheckBox.clearAndStir() {
    setAndStir(false)
}

private fun CheckBox.setAndStir(check : Boolean) {
    val queue = ArrayBlockingQueue<Boolean>(1)
    val listener  = ChangeListener<Boolean> { s, t, u -> queue.put(true) }

    selectedProperty().addListener(listener)
    Stirry.runOnPlatform { selectedProperty().set(check) }
    Stirry.waitForPlatform()
    queue.poll(1L, TimeUnit.SECONDS)
    selectedProperty().removeListener(listener)
}
