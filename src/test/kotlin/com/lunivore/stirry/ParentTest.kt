package com.lunivore.stirry

import javafx.scene.control.Button
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ParentTest() : AbstractExampleApplicationTest() {

    @Test
    fun `should be able to find children using predicates`() {
        var result = Stirry.findRoot().find<Button>({
            it is Button && it.text == "One" })

        assertTrue(result?.value.text == "One")
    }
}
