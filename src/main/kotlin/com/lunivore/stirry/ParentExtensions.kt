package com.lunivore.stirry

import javafx.scene.Node
import javafx.scene.Parent
import org.apache.logging.log4j.LogManager

fun <T : Parent> Parent.stirFind(predicate: (Node) -> Boolean): T? {
    val logger = LogManager.getLogger()

    logger.debug("Finding a node...")

    val found : Node = findFirst (
            this.childrenUnmodifiable,
            predicate) ?: throw Exception("Node not found")

    logger.debug("...node found of type ${found::class.java}")
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