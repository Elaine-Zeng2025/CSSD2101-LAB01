package edu.cssd2101.lab01;

import java.util.List;
import java.util.Optional;

/**
 * Insertion-ordered, ISBN-unique catalogue; implementations are not thread-safe.
 *
 * <p>Null arguments are rejected. Invalid ISBN syntax and blank search text are rejected rather
 * than treated as misses. Snapshots are detached. A duplicate add preserves the original entry.
 * This is a catalogue, not a stock counter.
 */
public interface BookstoreAPI {
    /**
     * Adds an entry if absent. Duplicate detection is O(n).
     *
     * @param book nonnull entry
     * @return true if added, false for an existing ISBN
     * @throws NullPointerException if book is null
     * @throws IllegalStateException if a fixed store has no free slot for a new ISBN
     */
    boolean add(Book book);

    /**
     * Removes the matching entry, preserving survivor order, in O(n).
     *
     * @param isbn syntactically valid ISBN
     * @return true if removed
     * @throws NullPointerException if isbn is null
     * @throws IllegalArgumentException if ISBN syntax is invalid
     */
    boolean removeByIsbn(String isbn);

    /**
     * Looks up identity in O(n).
     *
     * @param isbn syntactically valid ISBN
     * @return matching entry or empty
     * @throws NullPointerException if isbn is null
     * @throws IllegalArgumentException if syntax is invalid
     */
    default Optional<Book> findByIsbn(String isbn) {
        String key = Book.normalizeIsbn(isbn);
        return allBooks().stream().filter(b -> b.isbn().equals(key)).findFirst();
    }

    /**
     * Returns an immutable detached snapshot in insertion order, in O(n).
     *
     * @return nonnull list containing no nulls
     */
    List<Book> allBooks();

    /**
     * Counts stored entries in O(1).
     *
     * @return number of entries
     */
    int size();

    /**
     * Returns an independent, compact array in O(n).
     *
     * @return mutable detached array
     */
    default Book[] snapshotArray() {
        return allBooks().toArray(Book[]::new);
    }

    /**
     * Searches case-insensitive title substrings, in O(n) apart from text length.
     *
     * @param query nonblank text, stripped and normalized using Locale.ROOT
     * @return immutable matching snapshot in insertion order
     * @throws NullPointerException if query is null
     * @throws IllegalArgumentException if query is blank
     */
    default List<Book> findByTitle(String query) {
        String key = Book.text(query).toLowerCase(java.util.Locale.ROOT);
        return allBooks().stream()
                .filter(b -> b.title().toLowerCase(java.util.Locale.ROOT).contains(key))
                .toList();
    }

    /**
     * Searches case-insensitive author substrings in O(n).
     *
     * @param query nonblank query
     * @return immutable matching snapshot in insertion order
     * @throws NullPointerException if query is null
     * @throws IllegalArgumentException if query is blank
     */
    default List<Book> findByAuthor(String query) {
        // TODO T3: implement the documented extension contract.

        // creating the List to add matching books to
        //List<Book> correctAuthorBooks = new List<Book>();
        //default Book[] snapshotArray() {
            //return allBooks().toArray(Book[]::new);
        //}

        // --idea:
        // --take snapshot of correctAuthorBooks once done??? by doing List.copyOf(correctAuthorBooks)
        // (like how they did it in the allBooks() method
        // --

        // for loop is old idea, if using ArrayListBookstore doenst work then try for loop again
        //for (Book curBook : allBooks()) {
            //if (query.strip().compareToIgnoreCase(curBook.author()) == 0) {
                // add curBook to list
                //correctAuthorBooks.add(curBook);
            //}
        //}


            // maybe reference elaine's code instead of the loop so u can return snapshot

            // can also try using ArrayListBookstore(), like
            // ArrayListBookstore correctAuthorBooks = new ArrayListBookstore();
            // (in for loop, if author matches query) correctAuthorBooks.add(Book curBook);
            // after, call allBooks() to save snapshot of my correctAuthorBooks
            // ^like this: correctAuthorBooks.allBooks();

        // Creating a ArrayListBookstore object to store books with matching authors
        ArrayListBookstore correctAuthorBooks = new ArrayListBookstore();

        //if query is null, throw NullPointerException
        if (query == null) {
            throw new NullPointerException("author query is null");
        }

        // if query is blank, throw IllegalArgumentException
        if (query.isBlank()) {
            throw new IllegalArgumentException("author query is empty");
        }

        // For loop that loops through every book from the store, and checks if the author of it matches the query
        for (Book curBook : this.allBooks()) {
              //if (query.strip().compareToIgnoreCase(curBook.author()) == 0) {
                //if (query.strip().toLowerCase(java.util.Locale.ROOT).compareTo(curBook.author().toLowerCase()) == 0) {
            if (curBook.author().toLowerCase().contains(query.strip().toLowerCase(java.util.Locale.ROOT))) {
                  // adds the book with matching author to the list of books with a matching author
                  correctAuthorBooks.add(curBook);
              }
        }
        return (correctAuthorBooks.allBooks());


        //throw new UnsupportedOperationException("T3 is an exercise");
    }

    /**
     * Finds entries within inclusive cent bounds in O(n).
     *
     * @param minimum nonnegative lower bound
     * @param maximum upper bound at least minimum
     * @return immutable matches in insertion order
     * @throws IllegalArgumentException if bounds are invalid
     */
    default List<Book> findByPriceRange(long minimum, long maximum) {
        // TODO T3: implement the documented extension contract.

        // throws if the given minimum value is below 0
        if (minimum < 0) {
            throw new IllegalArgumentException("minimum must be a non-negative integar!!");
        }

        // throws if the given maximum value is smaller than the given minimum valye
        if (maximum < minimum) {
            throw new IllegalArgumentException("maximum must not be less than the minimum");
        }

        // creates an ArrayListBookstore object to store books within the specified price range
        ArrayListBookstore costFilteredBooks = new ArrayListBookstore();

        // for loop that loops through all of the bookstore's books and adds the ones within the price range into the costFilteredBooks array
        for (Book curBook : allBooks()) {
            if (curBook.priceCents() >= minimum && curBook.priceCents() <= maximum) {
                costFilteredBooks.add(curBook);
            }
        }

        return costFilteredBooks.allBooks();

        //throw new UnsupportedOperationException("T3 is an exercise -- findPriceRange incomplete");
    }

    /**
     * Finds entries by exact year in O(n); any integer query is permitted.
     *
     * @param year requested year
     * @return immutable matches in insertion order
     */
    default List<Book> findByYear(int year) {
        return allBooks().stream().filter(b -> b.year() == year).toList();
    }

    /**
     * Sums exact cents in O(n).
     *
     * @return zero for an empty catalogue
     * @throws ArithmeticException if the total exceeds long range
     */
    default long inventoryValueCents() {
        // Ivan
        // Create variable to add up all values
        long sum = 0;

        // Loop through all books and add their price to the sum
        for (Book book : allBooks()) {
            sum = Math.addExact(sum, book.priceCents());
        }
        return sum;
    }

    /**
     * Finds the highest price, choosing first insertion on ties, in O(n).
     *
     * @return expensive entry or empty
     */
    default Optional<Book> mostExpensive() {
        // Ivan
        // Create a new variable to hold the best book
        Book mostExpensive = null;

        // Loop through all the books 
        for (Book book : allBooks())
            // Compare if the current book is more expensive than the best book, if it is, set best to mostExpensive
            // mostExpensive == null sets the first book to mostExpensive to compare
            if (mostExpensive == null || book.priceCents() > mostExpensive.priceCents()) {
                mostExpensive = book;
            }
        // Returns the most expensive book, if there is no book, it will return an empty optional
        // Empty optional allows isEmpty() to work since it is a method that uses Optional to return True or False
        return Optional.ofNullable(mostExpensive);
    }

    /**
     * Finds the latest publication, choosing first insertion on ties, in O(n).
     *
     * @return latest entry or empty
     */
    default Optional<Book> mostRecent() {
        // Ivan
        // Set a variable for boook
        Book recent = null;

        // Loops through all books
        for (Book book : allBooks()) {
            // Sets first book to recent, then compares year of current book to next one.
            // If the book is more recent, it will set that book to recent and continue to compare until the end of the list
            if (recent == null || book.year() > recent.year()) {
                recent = book;
            }
        }

        // Returns the most expensive book, if there is no book, it will return an empty optional
        return Optional.ofNullable(recent);
    }
}
