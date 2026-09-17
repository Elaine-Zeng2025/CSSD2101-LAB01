package edu.cssd2101.lab01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void normalizationTest1() {
        assertEquals("2685823697X", Book.normalizeIsbn("268-5823  69X"));
    }

    @Test
    void normalizationTest2() {
        assertEquals("15768213496125X", Book.normalizeIsbn("157  68-2134 9612 X"));
    }

    @Test
    void normalizationTest3() {
        assertThrows(IllegalArgumentException.class, () -> {Book.normalizeIsbn("5 49-481 378912 11 34X");});
    }

    @Test
    void blankTextTest() {
        assertThrows(IllegalArgumentException.class, () -> {Book.text("-  -  ");});
    }

    @Test
    void nullTextTest() {

    }

}