package com.example.demo.repository;

import com.example.demo.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void whenFindById_thenReturnBook() {
        // given
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        entityManager.persist(book);
        entityManager.flush();

        // when
        Optional<Book> found = bookRepository.findById(book.getId());

        // then
        assertTrue(found.isPresent());
        assertEquals(book.getTitle(), found.get().getTitle());
        assertEquals(book.getAuthor(), found.get().getAuthor());
    }

    @Test
    void whenSaveBook_thenReturnSavedBook() {
        // given
        Book book = new Book();
        book.setTitle("Save Test Book");
        book.setAuthor("Save Test Author");

        // when
        Book savedBook = bookRepository.save(book);

        // then
        assertNotNull(savedBook.getId());
        assertEquals(book.getTitle(), savedBook.getTitle());
        assertEquals(book.getAuthor(), savedBook.getAuthor());
    }

    @Test
    void whenDeleteBook_thenBookShouldNotExist() {
        // given
        Book book = new Book();
        book.setTitle("Delete Test Book");
        book.setAuthor("Delete Test Author");
        entityManager.persist(book);
        entityManager.flush();

        // when
        bookRepository.deleteById(book.getId());

        // then
        Optional<Book> deletedBook = bookRepository.findById(book.getId());
        assertFalse(deletedBook.isPresent());
    }
}
