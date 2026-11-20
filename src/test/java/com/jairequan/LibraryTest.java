package com.jairequan;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

class LibraryTest {

    ArrayList<Integer> loanIds = new ArrayList<Integer>();
    ArrayList<Integer> memberIds = new ArrayList<Integer>();
    ArrayList<Integer> bookIds = new ArrayList<Integer>();

    MutableClock customDate;
    Library library;

    @BeforeEach 
    void setUp(){


        
        customDate = new MutableClock(Instant.parse("2026-01-01T12:00:00Z"), ZoneId.of("UTC"));

        library = new Library(customDate);

        memberIds.add(library.addMember("Bob", "William", "bw@gmail.com",
            "123-456-7890"));

        bookIds.add(library.addBook("0Jump Story", "Dat Guy", LocalDate.now(customDate),
        true, 99.99));
        bookIds.add(library.addBook("1Bald Peasant", "Dat Guy", LocalDate.now(customDate),
        true, 3.50));
        bookIds.add(library.addBook("2Dashing Fish", "Dat Guy", LocalDate.now(customDate),
        true, 3.50));
        bookIds.add(library.addBook("3Runaway Goof", "Dat Guy", LocalDate.now(customDate),
        true, 3.50));
        bookIds.add(library.addBook("4Awkward Club", "Dat Guy", LocalDate.now(customDate),
        true, 3.50));
        bookIds.add(library.addBook("5Cliff Hanging", "Dat Guy", LocalDate.now(customDate),
        true, 3.50));
    }

    @Test
    void unknownUserId(){
        assertThrows(IllegalArgumentException.class, () -> library.borrowBook(999, 0, 
            LocalDate.now(customDate).plusDays(3)));
    }

    @Test
    void unknownBookId(){
        assertThrows(IllegalArgumentException.class, () -> library.borrowBook(0, 999, 
            LocalDate.now(customDate).plusDays(3)));
    }

    @Test
    void unknownLoanId(){
        assertThrows(IndexOutOfBoundsException.class, () -> library.returnBook(loanIds.get(999)));
    }

    @Test 
    void borrowBookThenReturn(){
        loanIds.add(library.borrowBook(0, 0, LocalDate.now(customDate).plusDays(3)));
        assertFalse(library.isBookAvailable(0));
        
        library.returnBook(loanIds.get(0));
        assertTrue(library.isBookAvailable(0));

    }

    @Test
    void returnBookTwice(){
        loanIds.add(library.borrowBook(0, 0, LocalDate.now(customDate).plusDays(3)));
        library.returnBook(loanIds.get(0));
        
        assertThrows(IllegalStateException.class, () -> library.returnBook(loanIds.get(0)));

    }
    
    @Test
    void newBookIsNotLoaned() {
        Book book = new Book("T", "A", LocalDate.of(2020, 1, 1), true, 9.99, 0);
        assertFalse(book.isLoaned());
    }

    @Test
    void pastDueDate(){
        assertThrows(IllegalArgumentException.class, () -> loanIds.add(library.borrowBook(memberIds.get(0), bookIds.get(0), 
        LocalDate.now(customDate).minusDays(3))));
    }

    @Test
    void borrowSixBooks(){
        loanIds.add(library.borrowBook(memberIds.get(0), bookIds.get(0), LocalDate.now(customDate).plusDays(4)));
        loanIds.add(library.borrowBook(memberIds.get(0), bookIds.get(1), LocalDate.now(customDate).plusDays(5)));
        loanIds.add(library.borrowBook(memberIds.get(0), bookIds.get(2), LocalDate.now(customDate).plusDays(5)));
        loanIds.add(library.borrowBook(memberIds.get(0), bookIds.get(3), LocalDate.now(customDate).plusDays(7)));
        loanIds.add(library.borrowBook(memberIds.get(0), bookIds.get(4), LocalDate.now(customDate).plusDays(7)));

        assertThrows(IllegalArgumentException.class, () -> loanIds.add(library.borrowBook(memberIds.get(0), bookIds.get(5),
         LocalDate.now(customDate).plusDays(7))));
    }

    @Test
    void borrowLoanedBook(){
        loanIds.add(library.borrowBook(memberIds.get(0), bookIds.get(0), LocalDate.now(customDate).plusDays(4)));

        assertThrows(IllegalArgumentException.class, () -> loanIds.add(library.borrowBook(memberIds.get(0), bookIds.get(0),
         LocalDate.now(customDate).plusDays(7))));
    }

    @Test
    void borrowWithOverdueIsRejected(){
        loanIds.add(library.borrowBook(memberIds.get(0), bookIds.get(0), LocalDate.now(customDate).plusDays(2)));
        customDate.advanceDays(3);

        assertThrows(IllegalArgumentException.class, () -> loanIds.add(library.borrowBook(memberIds.get(0), bookIds.get(4), 
        LocalDate.now(customDate).plusDays(7))));
    }

}
