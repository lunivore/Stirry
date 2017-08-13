package com.lunivore.stirry

import com.lunivore.stirry.exampleapp.ExampleApp
import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

abstract class NodeTest {

    companion object {
        val logger = LogManager.getLogger()

        @JvmStatic
        @BeforeAll
        fun initializeStirry() {
            Stirry.initialize()
            logger.debug("Launching application...")
            Stirry.launchApp(ExampleApp())
            logger.debug("...application launched.")
        }

        @AfterAll
        fun stopStirry() {
            Stirry.stop()
        }
    }
}