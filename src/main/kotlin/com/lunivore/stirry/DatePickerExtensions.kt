package com.lunivore.stirry

import javafx.beans.value.ChangeListener
import javafx.scene.control.DatePicker
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit

fun  DatePicker.pickDateAndStir(dateAsString: String, format: String = "yyyy-MM-dd") {
    val formatter = DateTimeFormatter.ofPattern(format)
    val date = LocalDate.parse(dateAsString, formatter)

    val queue = ArrayBlockingQueue<Boolean>(1)
    val listener  = ChangeListener<LocalDate> { s, t, u -> queue.put(true) }
    valueProperty().addListener(listener)
    Stirry.runOnPlatform { value = date }
    Stirry.waitForPlatform()
    queue.poll(1L, TimeUnit.SECONDS)
    valueProperty().removeListener(listener)
}
