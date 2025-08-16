package com.example.demo.unit.model;

import com.example.demo.model.Book;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    @Test
    void whenSetId_thenGetCorrectId() {
        Book book = new Book();
        book.setId(1L);
        assertThat(book.getId()).isEqualTo(1L);
    }

    @Test
    void whenSetTitleAndAuthor_thenGetCorrectTitleAndAuthor() {
        Book book = new Book();
        book.setTitle("Test Title");
        book.setAuthor("Test Author");
        
        assertThat(book.getTitle()).isEqualTo("Test Title");
        assertThat(book.getAuthor()).isEqualTo("Test Author");
    }
}
