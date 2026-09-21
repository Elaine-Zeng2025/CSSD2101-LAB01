package edu.cssd2101.lab01;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/** Fixed-capacity array store: live entries occupy exactly indices zero through size-1. */
public final class FixedArrayBookstore implements BookstoreAPI {

    private final Book[] books;
    private int size;

    /**
     * Creates an empty bounded catalogue.
     *
     * @param capacity maximum entries, including zero
     * @throws IllegalArgumentException if capacity is negative
     */
    public FixedArrayBookstore(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must be nonnegative");
        }
        books = new Book[capacity];
    }

    /** {@inheritDoc} */
    @Override
    public boolean add(Book book) {
        Objects.requireNonNull(book, "book");

        for (int i = 0; i < size; i++) {
            if (books[i].equals(book)) {
                return false;
            }
        }

        if (size == books.length) {
            throw new IllegalStateException("catalogue is full");
        }

        books[size++] = book;
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public boolean removeByIsbn(String isbn) {
        Objects.requireNonNull(isbn, "isbn");

        String normalizedIsbn = Book.normalizeIsbn(isbn);

        // Find the book with the matching ISBN
        for (int i = 0; i < size; i++) {

            if (books[i].isbn().equals(normalizedIsbn)) {

                // Shift all following books one position to the left
                for (int j = i; j < size - 1; j++) {
                    books[j] = books[j + 1];
                }

                // Clear the unused last slot
                books[size - 1] = null;

                // Update the number of books
                size--;

                return true;
            }
        }

        // No matching ISBN was found
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public List<Book> allBooks() {
        return List.copyOf(
                Arrays.asList(
                        Arrays.copyOf(books, size)
                )
        );
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        return size;
    }
}