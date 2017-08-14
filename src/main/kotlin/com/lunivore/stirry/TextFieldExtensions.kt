package com.lunivore.stirry

import javafx.application.Platform
import javafx.beans.value.ChangeListener
import javafx.scene.control.TextField
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit

fun TextField.setTextAndStir(desiredText: String) {
    val queue = ArrayBlockingQueue<Boolean>(1)
    var listener : ChangeListener<String> = ChangeListener { s, t, u -> queue.put(true)}
    textProperty().addListener(listener)
    Platform.runLater({ text = desiredText })
    Stirry.waitForPlatform()
    queue.poll(1L, TimeUnit.SECONDS)
    textProperty().removeListener(listener)
}

fun <R: Any> TextField.waitForText(returnSuccessfulResultOrNull: (TextField) -> R?,
                                   timeoutInMilliseconds: Long = 2000) : Result<R> {

        val queue = ArrayBlockingQueue<Boolean>(1)
        var value : R? = returnSuccessfulResultOrNull(this)

        if (value == null) {
            val listener: ChangeListener<String> = ChangeListener<String>{ s, t, u ->
            value = returnSuccessfulResultOrNull(this)
                if (value != null) {
                    queue.put(true)
                }
            }

            this.textProperty().addListener(listener)
            queue.poll(timeoutInMilliseconds, TimeUnit.MILLISECONDS)
            this.textProperty().removeListener(listener)
        }

        // If the event didn't fire for some reason we want to do a last check
        // to see if anything we didn't spot changed our predicate
        if (value == null) { value = returnSuccessfulResultOrNull(this) }

        if (value == null) { return Result(null, "Timed out") } else { return Result(value, "") }

}
