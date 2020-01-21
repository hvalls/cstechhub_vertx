package com.cstechhub

import io.vertx.core.Vertx

fun main() {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(BookCatalogVerticle())
    vertx.deployVerticle(NotificationsVerticle())
}