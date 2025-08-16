package com.example.demo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testBookCreation() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");

        assertEquals(1L, book.getId());
        assertEquals("Test Book", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
    }

    @Test
    void testBookSettersAndGetters() {
        Book book = new Book();
        
        assertNull(book.getId());
        assertNull(book.getTitle());
        assertNull(book.getAuthor());

        book.setId(2L);
        book.setTitle("New Title");
        book.setAuthor("New Author");

        assertEquals(2L, book.getId());
        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
    }
}
