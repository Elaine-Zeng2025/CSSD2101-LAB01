package edu.cssd2101.lab01;

import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class BookModelTest {

    // Chloe
    // Test if normalizeIsbn() will return the right ISBN for a 9 digit ISBN
    @Test
    void normalizationTest1() {
        assertEquals("268582369X", Book.normalizeIsbn("    268-582369X   "));
    }

    // Chloe
    // Test if normalizeIsbn() will return the right ISBN for a 13 digit ISBN
    @Test
    void normalizationTest2() {
        assertEquals("1576821349612X", Book.normalizeIsbn("15768-21349612X        "));
    }

    // Chloe
    // Test if normalizeIsbn() will throw an exception for an ISBN with over 13 digits
    @Test
    void malformedIdentifierTest1() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book.normalizeIsbn("      549-4813789121134X");
        });
    }

    // Chloe
    // Test if normalizeIsbn() will throw an exception for an ISBN with under 9 digits
    @Test
    void malformedIdentifierTest2() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book.normalizeIsbn("  549-4134X    ");
        });
    }

    // Chloe
    // Test if normalizeIsbn() will throw an exception for an ISBN that doesn't end with a digit or 'X'
    @Test
    void malformedIdentifierTest3() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book.normalizeIsbn("268-582369x      ");
        });
    }

    // Chloe
    // Test if normalizeIsbn() will throw an exception for a null ISBN
    @Test
    void malformedIdentifierTest4() {
        assertThrows(NullPointerException.class, () -> {
            Book.normalizeIsbn(null);
        });
    }

    // Chloe
    // Test if text() will throw an exception for a blank input string
    @Test
    void blankTextTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book.text("    ");
        });
    }

    // Chloe
    // Test if text() will throw an exception for a null input string
    @Test
    void nullTextTest() {
        assertThrows(NullPointerException.class, () -> {
            Book.text(null);
        });
    }

    // Chloe
    // Test if year() will return the right minimum year
    @Test
    void yearTest1() {
       Book bookTest1 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 501294721123L, 1450);
       assertEquals(1450, bookTest1.year());
    }

    // Chloe
    // Test if year() will return the right maximum year
    @Test
    void yearTest2() {
        Book bookTest2 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 501294721123L, 2026);
        assertEquals(2026, bookTest2.year());
    }

    // Chloe
    // Test if year() will throw an exception if the year is below minimum
    @Test
    void yearTest3() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book bookTest3 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 501294721123L, 1449);
        });
    }

    // Chloe
    // Test if year() will throw an exception if the year is above maximum
    @Test
    void yearTest4() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book bookTest4 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 501294721123L, 2027);
        });
    }

    // Chloe
    // Test if priceCents() will take price if book is free
    @Test
    void freeBookTest() {
        Book bookTest5 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 0L, 2026);
        assertEquals(0L, bookTest5.priceCents());
    }

    // Chloe
    // Test if compareTo() returns 0 when ISBN are the same
    @Test
    void sameIsbnDifTitle1() {
        Book bookTest6 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 121417923L, 2026);
        Book bookTest7 = new Book("268-5823  69 X", "One Piece", "David Cluster", 121417923L, 2026);
        assertEquals(0, bookTest6.compareTo(bookTest7));
    }

    // Chloe
    // Test if equals() returns True when ISBN are the same
    @Test
    void sameIsbnDifTitle2() {
        Book bookTest8 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 121417923L, 2026);
        Book bookTest9 = new Book("268-5823  69 X", "One Piece", "David Cluster", 121417923L, 2026);
        assertTrue(bookTest8.equals(bookTest9));
    }

    // Chloe
    // Test if hashCode returns the same value for books when same ISBN
    @Test
    void sameIsbnDifTitle3() {
        Book bookTest10 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 121417923L, 2026);
        Book bookTest11 = new Book("268-5823  69 X", "One Piece", "David Cluster", 121417923L, 2026);
        assertEquals(bookTest10.hashCode(), bookTest11.hashCode());
    }

    // Chloe
    // Test if two books with same title but different ISBN remain in natural order in TreeSet
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