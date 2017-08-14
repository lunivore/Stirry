package com.lunivore.stirry

import javafx.event.Event
import javafx.event.EventHandler
import javafx.event.EventType
import javafx.scene.Node
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit


inline fun <reified T : Node, R : Any, E : Event> T.waitFor(
        crossinline waitAndReturnResult: (T) -> R?,
        timeoutInMilliseconds: Long = 2000,
        event: EventType<E>) : Result<R> {

    val queue = ArrayBlockingQueue<Boolean>(1)
    var value : R? = waitAndReturnResult(this)

    if (value == null) {
        val handler: EventHandler<E> = EventHandler<E> {
            value = waitAndReturnResult(this)
            if (value != null) {
                queue.put(true)
            }
        }

        this.addEventHandler(event, handler)
        queue.poll(timeoutInMilliseconds, TimeUnit.MILLISECONDS)
        this.removeEventHandler(event, handler)
    }

    // If the event didn't fire for some reason we want to do a last check
    // to see if anything we didn't spot changed our predicate
    if (value == null) { value = waitAndReturnResult(this) }

    if (value == null) { return Result(null, "Timed out") } else { return Result(value, "") }
}