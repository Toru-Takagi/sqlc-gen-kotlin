// Code generated by sqlc. DO NOT EDIT.
// versions:
//   sqlc v1.24.0

package com.example.booktest.postgresql

import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.sql.Types
import java.time.OffsetDateTime

const val booksByTags = """-- name: booksByTags :many
SELECT 
  book_id,
  title,
  name,
  isbn,
  tags
FROM books
LEFT JOIN authors ON books.author_id = authors.author_id
WHERE tags && ?::varchar[]
"""

data class BooksByTagsRow (
  val bookId: Int,
  val title: String,
  val name: String?,
  val isbn: String,
  val tags: List<String>
)

const val booksByTitleYear = """-- name: booksByTitleYear :many
SELECT book_id, author_id, isbn, book_type, title, year, available, tags FROM books
WHERE title = ? AND year = ?
"""

const val createAuthor = """-- name: createAuthor :one
INSERT INTO authors (name) VALUES (?)
RETURNING author_id, name
"""

const val createBook = """-- name: createBook :one
INSERT INTO books (
    author_id,
    isbn,
    book_type,
    title,
    year,
    available,
    tags
) VALUES (
    ?,
    ?,
    ?,
    ?,
    ?,
    ?,
    ?
)
RETURNING book_id, author_id, isbn, book_type, title, year, available, tags
"""

const val deleteBook = """-- name: deleteBook :exec
DELETE FROM books
WHERE book_id = ?
"""

const val getAuthor = """-- name: getAuthor :one
SELECT author_id, name FROM authors
WHERE author_id = ?
"""

const val getBook = """-- name: getBook :one
SELECT book_id, author_id, isbn, book_type, title, year, available, tags FROM books
WHERE book_id = ?
"""

const val updateBook = """-- name: updateBook :exec
UPDATE books
SET title = ?, tags = ?
WHERE book_id = ?
"""

const val updateBookISBN = """-- name: updateBookISBN :exec
UPDATE books
SET title = ?, tags = ?, isbn = ?
WHERE book_id = ?
"""

class QueriesImpl(private val conn: Connection) : Queries {

  @Throws(SQLException::class)
  override fun booksByTags(dollar1: List<String>): List<BooksByTagsRow> {
    return conn.prepareStatement(booksByTags).use { stmt ->
      stmt.setArray(1, conn.createArrayOf("pg_catalog.varchar", dollar1.toTypedArray()))

      val results = stmt.executeQuery()
      val ret = mutableListOf<BooksByTagsRow>()
      while (results.next()) {
          ret.add(BooksByTagsRow(
                results.getInt(1),
                results.getString(2),
                results.getString(3),
                results.getString(4),
                (results.getArray(5).array as Array<String>).toList()
            ))
      }
      ret
    }
  }

  @Throws(SQLException::class)
  override fun booksByTitleYear(title: String, year: Int): List<Book> {
    return conn.prepareStatement(booksByTitleYear).use { stmt ->
      stmt.setString(1, title)
          stmt.setInt(2, year)

      val results = stmt.executeQuery()
      val ret = mutableListOf<Book>()
      while (results.next()) {
          ret.add(Book(
                results.getInt(1),
                results.getInt(2),
                results.getString(3),
                BookType.lookup(results.getString(4))!!,
                results.getString(5),
                results.getInt(6),
                results.getObject(7, OffsetDateTime::class.java),
                (results.getArray(8).array as Array<String>).toList()
            ))
      }
      ret
    }
  }

  @Throws(SQLException::class)
  override fun createAuthor(name: String): Author? {
    return conn.prepareStatement(createAuthor).use { stmt ->
      stmt.setString(1, name)

      val results = stmt.executeQuery()
      if (!results.next()) {
        return null
      }
      val ret = Author(
                results.getInt(1),
                results.getString(2)
            )
      if (results.next()) {
          throw SQLException("expected one row in result set, but got many")
      }
      ret
    }
  }

  @Throws(SQLException::class)
  override fun createBook(
      authorId: Int,
      isbn: String,
      bookType: BookType,
      title: String,
      year: Int,
      available: OffsetDateTime,
      tags: List<String>): Book? {
    return conn.prepareStatement(createBook).use { stmt ->
      stmt.setInt(1, authorId)
          stmt.setString(2, isbn)
          stmt.setObject(3, bookType.value, Types.OTHER)
          stmt.setString(4, title)
          stmt.setInt(5, year)
          stmt.setObject(6, available)
          stmt.setArray(7, conn.createArrayOf("pg_catalog.varchar", tags.toTypedArray()))

      val results = stmt.executeQuery()
      if (!results.next()) {
        return null
      }
      val ret = Book(
                results.getInt(1),
                results.getInt(2),
                results.getString(3),
                BookType.lookup(results.getString(4))!!,
                results.getString(5),
                results.getInt(6),
                results.getObject(7, OffsetDateTime::class.java),
                (results.getArray(8).array as Array<String>).toList()
            )
      if (results.next()) {
          throw SQLException("expected one row in result set, but got many")
      }
      ret
    }
  }

  @Throws(SQLException::class)
  override fun deleteBook(bookId: Int) {
    conn.prepareStatement(deleteBook).use { stmt ->
      stmt.setInt(1, bookId)

      stmt.execute()
    }
  }

  @Throws(SQLException::class)
  override fun getAuthor(authorId: Int): Author? {
    return conn.prepareStatement(getAuthor).use { stmt ->
      stmt.setInt(1, authorId)

      val results = stmt.executeQuery()
      if (!results.next()) {
        return null
      }
      val ret = Author(
                results.getInt(1),
                results.getString(2)
            )
      if (results.next()) {
          throw SQLException("expected one row in result set, but got many")
      }
      ret
    }
  }

  @Throws(SQLException::class)
  override fun getBook(bookId: Int): Book? {
    return conn.prepareStatement(getBook).use { stmt ->
      stmt.setInt(1, bookId)

      val results = stmt.executeQuery()
      if (!results.next()) {
        return null
      }
      val ret = Book(
                results.getInt(1),
                results.getInt(2),
                results.getString(3),
                BookType.lookup(results.getString(4))!!,
                results.getString(5),
                results.getInt(6),
                results.getObject(7, OffsetDateTime::class.java),
                (results.getArray(8).array as Array<String>).toList()
            )
      if (results.next()) {
          throw SQLException("expected one row in result set, but got many")
      }
      ret
    }
  }

  @Throws(SQLException::class)
  override fun updateBook(
      title: String,
      tags: List<String>,
      bookId: Int) {
    conn.prepareStatement(updateBook).use { stmt ->
      stmt.setString(1, title)
          stmt.setArray(2, conn.createArrayOf("pg_catalog.varchar", tags.toTypedArray()))
          stmt.setInt(3, bookId)

      stmt.execute()
    }
  }

  @Throws(SQLException::class)
  override fun updateBookISBN(
      title: String,
      tags: List<String>,
      isbn: String,
      bookId: Int) {
    conn.prepareStatement(updateBookISBN).use { stmt ->
      stmt.setString(1, title)
          stmt.setArray(2, conn.createArrayOf("pg_catalog.varchar", tags.toTypedArray()))
          stmt.setString(3, isbn)
          stmt.setInt(4, bookId)

      stmt.execute()
    }
  }

}

