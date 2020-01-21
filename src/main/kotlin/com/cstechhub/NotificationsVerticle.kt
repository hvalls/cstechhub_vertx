package com.cstechhub

import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonObject

class NotificationsVerticle : AbstractVerticle() {

    override fun start() {
        vertx.eventBus().consumer<JsonObject>("book.created") {
            val bookJsonObject = it.body()

            println("Book created: ${bookJsonObject.getString("title")}, by ${bookJsonObject.getString("author")}")
        }
    }

}