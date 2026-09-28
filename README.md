# CSSD2101 Lab 01 — Encapsulation, Identity, and Interchangeable Catalogues

## Overview

This project implements a small bookstore catalogue whose behaviour stays the
same no matter which storage type is used underneath. The catalogue stores one
entry per ISBN, and each Book has a title, author, price (in cents), and year.
The same `BookstoreAPI` interface is implemented two ways — a growable
`ArrayList` and a fixed-capacity array — and both behave identically to any
caller.

We completed the missing methods (search, analytics, fixed-array removal, and
sparse-array utilities), wrote tests for the observable contracts, and kept the
immutable `Book` model unchanged.

## Team Members and Contributions

| # | Member | Task |
|---|--------|------|
| 1 | Chloe Balan | T1 — Book model audit + identity tests |
| 2 | Yurim Kim | T2 — Baseline API tests + two-storage comparison |
| 3 | Katie Lui | T3 (part 1) — author search + price-range search |
| 4 | Ivan Tieu | T3 (part 2) — inventory value, most expensive, most recent |
| 5 | Qinnan Yu | T4 — fixed-array removal |
| 6 | Elaine Yining Zeng | T5 — array utilities + integration & documentation lead |

## What Each Task Delivered

- **T1 (Book model):** Tests for ISBN normalization, free books, both year
  endpoints, invalid years, null/blank text, and malformed ISBNs. Verified that
  two books with the same normalized ISBN are equal, share a hash code, and
  compare as equal, while different ISBNs stay distinct in a `TreeSet`.
- **T2 (Baseline API):** One shared test helper runs the same add / lookup /
  duplicate / snapshot scenario on both implementations, proving a variable
  typed as `BookstoreAPI` works with either storage.
- **T3 (Search & analytics):** Author and price-range searches preserve
  insertion order and break ties by the first inserted entry. The inventory
  total uses `Math.addExact` to detect overflow. Empty analytics return zero for
  the total and an empty `Optional` for extremes.
- **T4 (Fixed-array removal):** Removal keeps the invariant, shifts survivors,
  and clears the vacated slot. A duplicate is detected before the capacity check;
  a new entry at full capacity throws `IllegalStateException` without changing
  contents.
- **T5 (Array utilities):** `filterPriceAtMost` uses a two-pass count-and-copy
  for an exactly sized compact array; `averagePrice` sums with `BigDecimal` and
  rounds once with HALF_EVEN; `removeDuplicates` keeps the first instance per
  ISBN in encounter order.

## Key Contract Decisions

- **Identity is the normalized ISBN only.** Equality, hash code, and natural
  order depend on ISBN, not on title or price. Display order (title, then ISBN)
  is separate and is not an identity ordering.
- **Adding a duplicate returns false and keeps the original object**, even if the
  incoming book has a different price.
- **Returned lists are immutable and detached; exported arrays are mutable and
  detached.** Neither is ever the store's internal container.
- **A miss is valid data (empty `Optional`); a null or malformed argument is a
  programmer error (throws an exception).**
- **No data vs. free is a real difference:** an empty average returns an empty
  `Optional`, while one free book returns 0.00.

## Bug Found Through Testing

While reviewing the T1 tests, several normalization tests failed with
`IllegalArgumentException`. The tests assumed `normalizeIsbn` removed *all*
whitespace, but the contract only strips *surrounding* whitespace and removes
hyphens — inner spaces are kept ("interior text remains unchanged").

- **Smallest input that reproduced it:** any ISBN with a space in the middle,
  e.g. `"268-5823 69 X"`.
- **Root cause:** the test expectation was wrong, not the model.
- **Fix:** the test inputs were changed to ISBNs that are valid after removing
  hyphens only. We did *not* loosen the model's validation, so the identity
  invariant is preserved.

## How to Build and Test

Run from the project folder:

```
./mvnw -Pacceptance clean test
```

This runs the full acceptance suite (Book, baseline, store contract, and array
utility tests). A successful run reports `BUILD SUCCESS` with no failures,
errors, or skipped tests. The baseline smoke tests alone run with
`./mvnw clean test`.

## Container Ownership

- `Book` — owns validated, immutable metadata; ISBN defines identity.
- `BookstoreAPI` — defines operations and shared query algorithms.
- `ArrayListBookstore` — owns a private dynamic list and enforces unique ISBNs.
- `FixedArrayBookstore` — owns a private fixed-capacity array and a live count.
- `BookArrayUtils` — processes caller-owned sparse arrays with explicit rules.
