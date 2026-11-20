package com.jairequan;

import java.util.HashMap;
import java.time.*;


public class Library{

    private HashMap<Integer ,Member> members = new HashMap<>();
    private HashMap<Integer, Book> books = new HashMap<>();
    private HashMap<Integer, Loan> loans = new HashMap<>();
    private HashMap<Integer, Loan> archivedLoans = new HashMap<>();
    private int memIdNum = 0;
    private int bookIdNum = 0;
    private int loanIdNum = 0;
    private LocalDate overDueCheckedToday;
    private Clock customDate;

    public Library(){
        this.customDate = Clock.systemDefaultZone();
        overDueCheckedToday = LocalDate.now(this.customDate).minusDays(1);
    }

    public Library(Clock customDate){
        this.customDate = customDate;
        overDueCheckedToday = LocalDate.now(this.customDate).minusDays(1);
    }

    public int addMember(String fName, String lName, String email, String pNum){ 

        System.out.println("making member");
        Member member = new Member(fName, lName, email, pNum, memIdNum);
        members.put(memIdNum, member);
        memIdNum++;
        return memIdNum-1;
    }

    public int addBook(String title, String author, LocalDate publishDate,
        boolean isHardCover, double price){

        System.out.println("making book");
        Book book = new Book(title, author, publishDate, isHardCover, price, bookIdNum);
        books.put(bookIdNum, book);
        bookIdNum++;
        return bookIdNum-1;
    }
    
    

    public int borrowBook(int memberId, int bookId, LocalDate dueDate){
        System.out.println("Library class making loan");

        overDueSweep();
        Loan loan = new Loan(getMemberById(memberId), getBookById(bookId), dueDate, loanIdNum, customDate);
        loans.put(loanIdNum, loan);
        loanIdNum++;
        return loanIdNum-1;
    
    }

    private Loan getLoanById(int Id){
        Loan temp = loans.get(Id);
        if(temp != null){
            return temp;
        }

        throw new IllegalArgumentException("No loan with ID of " + Id);
    }

    private Loan getArchivedLoanById(int Id){
        Loan temp = archivedLoans.get(Id);
        if(temp != null){
            return temp;
        }

        throw new IllegalArgumentException("No archived loan with ID of " + Id);
    }

    private Member getMemberById(int Id){
        Member temp = members.get(Id);
        if(temp != null){
            return temp;
        }

        throw new IllegalArgumentException("No member with ID of " + Id);
    }


    private Book getBookById(int Id){
        Book temp = books.get(Id);
        if(temp != null){
            return temp;
        }

        throw new IllegalArgumentException("No book with ID of " + Id);
    }

    public String getBookTitleById(int Id){
        return getBookById(Id).getTitle();
    }

    public void printLoans(){
        System.out.println("Active Loans:");
        for(Loan loan: loans.values()){
            System.out.println(loan);
        }

        System.out.println("Archived Loans");
        for(Loan loan: archivedLoans.values()){
            System.out.println(loan);
        }
    }

    public void returnBook(int loanId){   
        if(archivedLoans.containsKey(loanId)){
            getArchivedLoanById(loanId).returnBook();
        }else{
        Loan loan = getLoanById(loanId);
        loan.returnBook();
        archivedLoans.put(loanId, loan);
        loans.remove(loanId);
        }
    }


    public boolean isBookAvailable(int id){
        return !getBookById(id).isLoaned();
    }

    private void overDueSweep(){
        if(!overDueCheckedToday.isEqual(LocalDate.now(customDate))){
            for(Loan loan : loans.values()){
                loan.overDue();
            }

            overDueCheckedToday = LocalDate.now(customDate);
        }
        
    }




}