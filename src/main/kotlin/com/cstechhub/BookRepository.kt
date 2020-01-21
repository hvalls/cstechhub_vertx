package com.cstechhub

object BookRepository {

    private val data = mutableMapOf(
        "1" to Book("1", "Clean Code", "Robert C. Martin"),
        "2" to Book("2", "El Guardian Invisible", "Dolores Redondo")
    )

    fun findAll(): List<Book> {
        Thread.sleep(4000)
        return data.values.toList()
    }

    fun save(title: String, author: String): Book {
        val id = (data.size + 1).toString()
        data[id] = Book(id, title, author)
        return data[id]!!
    }

}