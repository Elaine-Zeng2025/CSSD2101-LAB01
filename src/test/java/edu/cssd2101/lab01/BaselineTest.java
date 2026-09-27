package edu.cssd2101.lab01;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class BaselineTest {
    @Test
    void bothStoresPreserveIdentityAndSnapshots() {
        for (BookstoreAPI store :
                java.util.List.of(new ArrayListBookstore(), new FixedArrayBookstore(2))) {
            Book book = new Book("9780134685991", "Effective Java", "Joshua Bloch", 4599, 2018);
            assertTrue(store.add(book));
            assertFalse(store.add(new Book("978-0134685991", "Changed", "Other", 0, 2026)));
            assertSame(book, store.findByIsbn("9780134685991").orElseThrow());
            Book[] copy = store.snapshotArray();
            copy[0] = null;
            assertEquals(1, store.size());
            List<Book> oldSnapshot = store.allBooks();
            Book secondBook = new Book("1098121228", "Python Data Science Handbook", "Jake VanderPlas", 3196, 2016);
            assertTrue(store.add(secondBook));
            assertEquals(List.of(book),oldSnapshot);
            assertEquals(List.of(book,secondBook),store.allBooks());
            
            assertEquals(java.util.List.of(book), store.findByTitle("JAVA"));
            assertThrows(UnsupportedOperationException.class, () -> store.allBooks().clear());
        }
    }



    @Test
    void invalidBookFailsBeforeItCanBeStored() {
        assertThrows(
                IllegalArgumentException.class, () -> new Book("bad", "Title", "Author", 0, 2026));
        assertThrows(
                IllegalArgumentException.class,
                () -> new Book("0132350882", "Title", "Author", -1, 2026));
    }
}
