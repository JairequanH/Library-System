package com.jairequan;

import java.time.*;

public class Book{
    private String title;
    private String author;
    private LocalDate publishDate;
    private boolean isHardCover;
    private boolean isLoaned = false;
    private double price;
    final private int bookID;

    public Book(String title, String author, LocalDate publishDate,
        boolean isHardCover, double price, int bookID){
        
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.isHardCover = isHardCover;
        this.price = price;
        this.bookID = bookID;
    }


    public String getTitle(){
        return this.title;
    }

    public String getAuthor(){
        return this.author;
    }

    public LocalDate getPublishDate(){
        return this.publishDate;
    }

    public boolean getCoverType(){
        return this.isHardCover;
    }

    public boolean isLoaned(){
        return this.isLoaned;
    }

    public double getPrice(){
        return this.price;
    }

    public int getBookID(){
        return this.bookID;
    }

    public void bookLoaned(){
        this.isLoaned = true;
        System.out.println("Book \"" + title + "\" is loaned: " + this.isLoaned);
        
    }

    public void bookReturned(){
        this.isLoaned = false;
        System.out.println("Book \"" + title + "\" is loaned: " + this.isLoaned);
        
    }

    
}