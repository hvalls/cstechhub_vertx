package com.cstechhub

import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
class BookCatalogVerticleTest {

    @BeforeEach
    fun setUp(vertx: Vertx, testContext: VertxTestContext) {
        vertx.deployVerticle(BookCatalogVerticle()) {
            testContext.completeNow()
        }
    }

    @Test
    fun getBooksTest(vertx: Vertx, testContext: VertxTestContext) {
        val client = WebClient.create(vertx)

        client.getAbs("http://localhost:8080/books").send {
            testContext.verify {
                val response = it.result()

                val jsonArray = response.bodyAsJsonArray()

                assertEquals(jsonArray.size(), 2)

                testContext.completeNow()
            }
        }

    }

}