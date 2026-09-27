package edu.cssd2101.lab01;

import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class BookModelTest {

    @Test
    void normalizationTest1() {
        assertEquals("268582369X", Book.normalizeIsbn("    268-582369X   "));
    }

    @Test
    void normalizationTest2() {
        assertEquals("15768213496124", Book.normalizeIsbn("15768-213496124        "));
    }

    @Test
    void malformedIdentifierTest1() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book.normalizeIsbn("      549-4813789121134X");
        });
    }

    @Test
    void malformedIdentifierTest2() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book.normalizeIsbn("  549-4134X    ");
        });
    }

    @Test
    void malformedIdentifierTest3() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book.normalizeIsbn("268-582369x      ");
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
            Book.text("    ");
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
       Book bookTest1 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 501294721123L, 1450);
       assertEquals(1450, bookTest1.year());
    }

    @Test
    void yearTest2() {
        Book bookTest2 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 501294721123L, 2027);
        assertEquals(2026, bookTest2.year());
    }

    @Test
    void yearTest3() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book bookTest3 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 501294721123L, 1449);
        });
    }

    @Test
    void yearTest4() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book bookTest4 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 501294721123L, 2028);
        });
    }

    @Test
    void freeBookTest() {
        Book bookTest5 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 0L, 2026);
        assertEquals(0L, bookTest5.priceCents());
    }

    @Test
    void sameIsbnDifTitle1() {
        Book bookTest6 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 121417923L, 2026);
        Book bookTest7 = new Book("268-5823  69 X", "One Piece", "David Cluster", 121417923L, 2026);
        assertEquals(0, bookTest6.compareTo(bookTest7));
    }

    @Test
    void sameIsbnDifTitle2() {
        Book bookTest8 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 121417923L, 2026);
        Book bookTest9 = new Book("268-5823  69 X", "One Piece", "David Cluster", 121417923L, 2026);
        assertTrue(bookTest8.equals(bookTest9));
    }

    @Test
    void sameIsbnDifTitle3() {
        Book bookTest10 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 121417923L, 2026);
        Book bookTest11 = new Book("268-5823  69 X", "One Piece", "David Cluster", 121417923L, 2026);
        assertEquals(bookTest10.hashCode(), bookTest11.hashCode());
    }

    @Test
    void difIsbnSameTitle() {
        Book bookTest12 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 121417923L, 2026);
        Book bookTest13 = new Book("2103-  239 -98 X", "Too Moral", "David Cluster", 121417923L, 2026);
        assertNotEquals(0, bookTest12.compareTo(bookTest13));
        TreeSet<Book> bookTests = new TreeSet<>();
        bookTests.add(bookTest12);
        bookTests.add(bookTest13);
        assertEquals(2, bookTests.size());
    }

}