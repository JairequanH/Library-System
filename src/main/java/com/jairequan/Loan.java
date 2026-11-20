package com.jairequan;

import java.time.*;

public class Loan {
    final private Member member;
    final private Book book;
    final private LocalDate startDate;
    private LocalDate returnDate;
    private LocalDate dueDate;
    private boolean isOverdue = false;
    final private int loanId;
    private static final int MAX_BOOKS = 5;
    private Clock currentDate;

    public Loan(Member member, Book book, LocalDate dueDate, int loanId, Clock currentDate){
        if(book.isLoaned()){
            throw new IllegalArgumentException("Cannot be loaned, " + book.getTitle() + " is already loaned");
        }

        if(member.getOverdueNum() > 0){
            throw new IllegalArgumentException("Cannot take loan, " + member.getName() + " has overdue book");
        }

        if(member.getBookNum() >= MAX_BOOKS){
            throw new IllegalArgumentException("Cannot take loan, " + member.getName() + " has " + MAX_BOOKS + " books loaned already");
        }

        if(!dueDate.isAfter(LocalDate.now(currentDate))){
            throw new IllegalArgumentException("Cannot make loan, due date is in past");
        }

        this.member = member;
        this.book = book;
        this.startDate = LocalDate.now(currentDate);
        this.dueDate = dueDate;
        this.loanId = loanId;
        this.currentDate = currentDate;

        book.bookLoaned();
        member.addBook(); 
        System.out.println("Loan started successfully");
    }

    public void overDue(){
        if(LocalDate.now(currentDate).isAfter(this.dueDate) && this.returnDate == null
         && !this.isOverdue){
            System.out.println(this.book.getTitle() + " is overdue, adding to " + this.member.getName() + "'s overdue books");
            this.isOverdue = true;
            this.member.addOverdue();
        }else if(this.isOverdue){
            System.out.println(this.book.getTitle() + " has already been marked as overdue");
        }else{
            System.out.println(this.book.getTitle() + " is not overdue yet");
        }
    }

    public void returnBook(){
        if(this.returnDate == null){
            System.out.println("Finalizing loan");
            this.member.subBook();
            this.book.bookReturned();
            this.returnDate = LocalDate.now(currentDate);

            if(this.isOverdue){
                System.out.println(this.book.getTitle() + " was overdue, subtracting from " + this.member.getName() + "'s overdue books");
                this.member.subOverdue();
            }
        }else{
            throw new IllegalStateException(this.book.getTitle() + " was returned twice for some reason");
        }
        
    }

    public int getId(){
        return this.loanId;
    }

    @Override 
    public String toString(){
        return "Loan ->     Member: " + this.member.getName() + "   Book: " + this.book.getTitle()
        + "     Due Date: " + this.dueDate + "      Return Date: " + this.returnDate + "    Overdue: " + this.isOverdue;
    }

}
