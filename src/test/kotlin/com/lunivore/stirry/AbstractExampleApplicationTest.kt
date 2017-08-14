package com.lunivore.stirry

import com.lunivore.stirry.exampleapp.ExampleApp
import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

abstract class AbstractExampleApplicationTest {

        val logger = LogManager.getLogger()

    @BeforeEach
    fun initializeStirry() {
        Stirry.initialize()
        logger.debug("Launching application...")
        Stirry.launchApp(ExampleApp())
        logger.debug("...application launched.")
    }

    @AfterEach
    fun stopStirry() {
        Stirry.stop()
    }
}