package edu.cssd2101.lab01;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void normalizationTest1() {
        assertEquals("268582369X", Book.normalizeIsbn("268-5823  69 X"));
    }

    @Test
    void normalizationTest2() {
        assertEquals("1576821349612X", Book.normalizeIsbn("157  68-2134 9612 X"));
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
       Book bookTest1 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 501294721123L, 1450);
       assertEquals(1450, bookTest1.year());
    }

    @Test
    void yearTest2() {
        Book bookTest2 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 501294721123L, 2026);
        assertEquals(2026, bookTest2.year());
    }

    @Test
    void yearTest3() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book bookTest3 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 501294721123L, 1451);
        });
    }

    @Test
    void yearTest4() {
        assertThrows(IllegalArgumentException.class, () -> {
            Book bookTest4 = new Book("268-5823  69 X", "Too Moral", "David Cluster", 501294721123L, 2027);
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
        TreeSet<Book> bookTests = new TreeSet<>();
        bookTests.add(bookTest13);
        bookTests.add(bookTest12);
        Iterator<Book> bookTestsIterations = bookTests.iterator();
        assertEquals(bookTest12, bookTestsIterations.next());
        assertEquals(bookTest13, bookTestsIterations.next());
    }

}