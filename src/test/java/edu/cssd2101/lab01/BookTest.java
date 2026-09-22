package edu.cssd2101.lab01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void normalizationTest1() {
        assertEquals("2685823697X", Book.normalizeIsbn("268-5823  69 X"));
    }

    @Test
    void normalizationTest2() {
        assertEquals("15768213496125X", Book.normalizeIsbn("157  68-2134 9612 X"));
    }

    @Test
    void malformedIdentifierTest1() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book.normalizeIsbn("5 49-481 378912 11 34X");
        });
    }

    @Test
    void malformedIdentifierTest2() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book.normalizeIsbn("5 49-41 34X");
        });
    }

    @Test
    void malformedIdentifierTest3() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book.normalizeIsbn("268-5823  69x");
        });
    }

    @Test
    void malformedIdentifierTest4() {
        assertThrows(NullPointerException.class, () -> {
            Book.normalizeIsbn(null);
        });
    }

    @Test
    void blankTextTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book.text("-  -  ");
        });
    }

    @Test
    void nullTextTest() {
        assertThrows(NullPointerException.class, () -> {
            Book.text(null);
        });
    }

    @Test
    void yearTest1() {
       Book bookTest1 = new Book("6512--47  98 7 5X", "Too Moral", "David Cluster", 501294721123L, 1450);
       assertEquals(1450, bookTest1.year());
    }

    @Test
    void yearTest2() {
        Book bookTest2 = new Book("6512--47  98 7 5X", "Too Moral", "David Cluster", 501294721123L, 2026);
        assertEquals(2026, bookTest2.year());
    }

    @Test
    void yearTest3() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book bookTest3 = new Book("6512--47  98 7 5X", "Too Moral", "David Cluster", 501294721123L, 1451);
        });
    }

    @Test
    void yearTest4() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book bookTest4 = new Book("6512--47  98 7 5X", "Too Moral", "David Cluster", 501294721123L, 2027);
        });
    }

    @Test
    void freeBookTest() {
        Book bookTest2 = new Book("6512--47  98 7 5X", "Too Moral", "David Cluster", 0L, 2026);
        assertEquals(0L, bookTest2.priceCents());
    }

}