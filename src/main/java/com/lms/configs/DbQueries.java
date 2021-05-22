package com.lms.configs;

public interface DbQueries {

    public static final String ADD_BOOK = "INSERT INTO BOOK (book_name,book_title,genre,stock,created_date,updated_date) " +
                                            "VALUES(?,?,?,?,?,?)";
    public static final String ADD_USER = "INSERT INTO USER (first_name,last_name,email,password,address) VALUES(?,?,?,?,?)";
    public static final String GET_ALL_BOOKS ="SELECT * FROM BOOK";
    public static final String GET_USER_BOOK_COUNT="SELECT COUNT(userId) FROM ISSUE WHERE userId= ?";
    public static final String GET_BOOK_STOCK ="SELECT stock FROM BOOK WHERE id=?";
    public static final String DOES_USER_EXIST ="SELECT COUNT(id) FROM USER WHERE id=?";
    public static final String ALLOCATE_COPY_OF_A_BOOK = "UPDATE BOOK SET STOCK=? WHERE id=?";
    public static final String ISSUE_BOOK ="INSERT INTO ISSUE (issuedDate,returnDate,userId,bookId) VALUES(?,?,?,?)";
    public static final String FETCH_A_ISSUE_RECORD ="SELECT * FROM ISSUE WHERE userId=? AND bookId=?";
    public static final String RETURN_BOOK ="INSERT INTO RETURNTABLE (issuedDate,returnDate,userId,bookId) VALUES(?,?,?,?)";
    public static final String DELETE_ISSUE_RECORD = "DELETE FROM ISSUE WHERE WHERE userId=? AND bookId=?";
}
