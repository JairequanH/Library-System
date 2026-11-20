package com.jairequan;

public class Member{
    private String fName;
    private String lName;
    private String email;
    private String pNum;
    private int numOfBooks = 0;
    private int numOfOverdue = 0;
    final private int memberID;

    public Member(String fName, String lName, String email, 
        String pNum, int memberID){
            this.fName = fName;
            this.lName = lName;
            this.email = email;
            this.pNum = pNum;
            this.memberID = memberID;
    }

    public String getName(){
        return this.fName + " " + this.lName;
    }

    public String getEmail(){
        return this.email;
    }

    public String getpNum(){
        return this.pNum;
    }

    public int getBookNum(){
        return this.numOfBooks;
    }

    public int getOverdueNum(){
        return this.numOfOverdue;
    }

    public int getMemberID(){
        return this.memberID;
    }

    public void addBook(){
        
        this.numOfBooks ++;
        System.out.println("Increasing " + fName + "'s book num: " + this.numOfBooks);
    }

    public void subBook(){
        if(this.numOfBooks>0){
            this.numOfBooks--;
            System.out.println("Decreasing " + fName + "'s book num: " + this.numOfBooks);
            
        }else{
            throw new IllegalStateException("Return error or corrupted information: Member would have negative books checked out");
        }
    }

    public void addOverdue(){
        this.numOfOverdue++;
        System.out.println("Increasing " + fName + "'s overdue book num: " + this.numOfOverdue);
        
    }

    public void subOverdue(){
        if(this.numOfOverdue > 0){
            this.numOfOverdue--;
            System.out.println("Decreasing " + fName + "'s overdue book num: " + this.numOfOverdue);
            
        }else{
            throw new IllegalStateException("Return error or corrupted information: Member would have negative overdue books checked out");

        }
    }


}