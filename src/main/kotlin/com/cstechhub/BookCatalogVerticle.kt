package com.cstechhub

import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler

class BookCatalogVerticle : AbstractVerticle() {

    override fun start() {
        val router = Router.router(vertx)

        router.route().handler(BodyHandler.create())

        router.route().failureHandler { context ->
            context.failure().printStackTrace()
            context.next()
        }

        router.get("/books").handler { context: RoutingContext ->
            getBooks(context)
        }

        router.post("/books").handler { context ->
            createBook(context)
        }

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8080) {
                println("Server started")
            }
    }

    private fun createBook(context: RoutingContext) {
        val body = context.bodyAsJson
        val title = body.getString("title")
        val author = body.getString("author")

        val book: Book = BookRepository.save(title, author)

        val jsonObject = JsonObject.mapFrom(book)
        context.response().end(
            jsonObject.encodePrettily()
        )

        vertx.eventBus().publish("book.created", jsonObject)
    }

    private fun getBooks(context: RoutingContext) {
        vertx.executeBlocking<List<Book>>({ promise ->
            val books = BookRepository.findAll()
            promise.complete(books)
        }, { result ->
            val books = result.result()
            val jsonArray = JsonArray(books)
            context.response().end(jsonArray.encodePrettily())
        })
    }

}