package com.lunivore.stirry

import javafx.scene.control.DatePicker
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class DatePickerTest : AbstractExampleApplicationTest() {

    @Test
    fun `should be able to pick a date in a date picker`() {
        // Given the example app is running (which it is)

        // When we find the date picker and pick a date in it
        val datePicker = Stirry.findInRoot<DatePicker> { true }
        datePicker.value.pickDateAndStir("2017-04-04")

        // Then we should be able to read that date back
        assertEquals(LocalDate.of(2017, 4, 4), datePicker.value?.value)
    }
}