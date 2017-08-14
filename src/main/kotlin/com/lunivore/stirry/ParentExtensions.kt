package com.lunivore.stirry

import javafx.scene.Node
import javafx.scene.Parent
import java.util.*

inline fun <reified T : Node> Parent.find(predicate: (T) -> Boolean): Result<T> {

    val stack = Stack<Node>()
    stack.push(this)
    while(!stack.empty()) {
        val candidate = stack.pop()
        if (candidate is T && predicate(candidate)) {
            return Result<T>(candidate, "")
        }
        if (candidate is Parent) { candidate.childrenUnmodifiable.forEach {stack.push(it)} }
    }
    return Result(null, "Failed to find node of type ${T::class.java}")
}
