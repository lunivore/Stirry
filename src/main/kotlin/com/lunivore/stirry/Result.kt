package com.lunivore.stirry

class Result<T>(private val nullableValue: T?, val message: String) {
    val succeeded = nullableValue != null
    val value : T
        get() {
            if (nullableValue == null) { throw IllegalStateException("Attempted to use value from failed result: $message") }
            return nullableValue
        }

}
