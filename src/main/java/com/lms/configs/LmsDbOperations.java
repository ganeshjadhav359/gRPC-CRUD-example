package com.lms.configs;

import com.lms.model.Book;
import com.lms.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LmsDbOperations {
    public static final Logger logger = Logger.getLogger(LmsDbOperations.class.getName());

    public static void addBook(Book book) throws SQLException{

            Connection connection = DataSourceManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(DbQueries.ADD_BOOK);

            preparedStatement.setString(1,book.getName());
            preparedStatement.setString(2,book.getTitle());
            preparedStatement.setString(3,book.getGenre());
            preparedStatement.setInt(4,book.getStock());
            preparedStatement.setString(5,book.getCreated_date());
            preparedStatement.setString(6,book.getUpdated_date());

            preparedStatement.execute();
            logger.fine("book with title: "+book.getTitle()+" inserted in book table");

    }

    public static void addUser(User user) throws SQLException {

        Connection connection = DataSourceManager.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(DbQueries.ADD_USER);

        preparedStatement.setString(1,user.getFirstName());
        preparedStatement.setString(2,user.getLastName());
        preparedStatement.setString(3,user.getEmail());
        preparedStatement.setString(4,user.getPassword());
        preparedStatement.setString(5,user.getAddress());

        preparedStatement.execute();
        logger.fine("user : "+user.getFirstName()+" inserted in user table");

    }

    public static List<com.lmsService.grpc.Book> getAllBooks() throws SQLException {
        Connection connection = DataSourceManager.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(DbQueries.GET_ALL_BOOKS);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<com.lmsService.grpc.Book> bookList = new ArrayList<>();

        while (resultSet.next()){
            bookList.add(com.lmsService.grpc.Book.newBuilder()
                    .setName(resultSet.getString(2))
                    .setTitle(resultSet.getString(3))
                    .setGenre(resultSet.getString(4))
                    .setStock(resultSet.getInt(5))
                    .build()
            );
        }
        return bookList;
    }

    public static void issueBook(int bookId, int userId) throws SQLException {
        Connection connection = DataSourceManager.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(DbQueries.DOES_USER_EXIST);
        preparedStatement.setInt(1,userId);
        ResultSet rs = preparedStatement.executeQuery();
        int checkUser;
        if(rs.next()){
            checkUser = rs.getInt(1);
            if(checkUser==0){
                System.out.println("invalid userId");
                return;
            }
            else
                System.out.println("valid userId");
        }
        else{
            System.out.println("something went wrong");
        }


        preparedStatement = connection.prepareStatement(DbQueries.GET_USER_BOOK_COUNT);
        preparedStatement.setInt(1,userId);
        rs = preparedStatement.executeQuery();

        int count =0;
        if(rs.next()){
            count = rs.getInt(1);
            System.out.println("already issued books :"+count);
        }
        else{
            System.out.println("something went wrong");
        }
        if(count>=Constants.MAX_BOOKS_ISSUED_TO_A_USER){
            System.out.println("you have been already issued enough books");
            return;
        }

        preparedStatement = connection.prepareStatement(DbQueries.GET_BOOK_STOCK);
        preparedStatement.setInt(1,bookId);
        rs = preparedStatement.executeQuery();
        int stock =0;
        if(rs.next()){
            stock = rs.getInt(1);
            System.out.println("stock of books :"+stock);
            if(stock==0){
                return;
            }

        }
        else{
            System.out.println("something went wrong");
        }

        String returnDate = JavaDateMySqlDate.getReturnDate();

        preparedStatement = connection.prepareStatement(DbQueries.ALLOCATE_COPY_OF_A_BOOK);
        preparedStatement.setInt(1,stock-1);
        preparedStatement.setInt(2,bookId);
        preparedStatement.execute();

        preparedStatement = connection.prepareStatement(DbQueries.ISSUE_BOOK);
        preparedStatement.setString(1,JavaDateMySqlDate.getDateTime());
        preparedStatement.setString(2,JavaDateMySqlDate.getReturnDate());
        preparedStatement.setInt(3,userId);
        preparedStatement.setInt(4,bookId);
        preparedStatement.execute();

        connection.commit();
    }

    public static void returnBook(int bookId, int userId) throws SQLException {
        Connection connection = DataSourceManager.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(DbQueries.DOES_USER_EXIST);
        preparedStatement.setInt(1,userId);
        ResultSet rs = preparedStatement.executeQuery();
        int checkUser;
        if(rs.next()){
            checkUser = rs.getInt(1);
            if(checkUser==0){
                System.out.println("invalid userId");
                return;
            }
            else
                System.out.println("valid userId");
        }
        else{
            System.out.println("something went wrong");
        }

        preparedStatement = connection.prepareStatement(DbQueries.FETCH_A_ISSUE_RECORD);
        preparedStatement.setInt(1,userId);
        preparedStatement.setInt(2,bookId);
        rs = preparedStatement.executeQuery();
        if(rs.next()){
            System.out.println("record exists");
        }
        else {
            System.out.println("please provide correct userId and bookId");
            return;
        }
        preparedStatement = connection.prepareStatement(DbQueries.RETURN_BOOK);
        preparedStatement.setString(1,rs.getString(2));
        preparedStatement.setString(2,JavaDateMySqlDate.getDateTime());
        preparedStatement.setInt(3,userId);
        preparedStatement.setInt(4,bookId);
        preparedStatement.execute();

        preparedStatement = connection.prepareStatement(DbQueries.DELETE_ISSUE_RECORD);
        preparedStatement.setInt(1,userId);
        preparedStatement.setInt(2,bookId);
        preparedStatement.execute();

        preparedStatement = connection.prepareStatement(DbQueries.GET_BOOK_STOCK);
        preparedStatement.setInt(1,bookId);
        rs = preparedStatement.executeQuery();
        int stock =0;
        if(rs.next()){
            stock = rs.getInt(1);
            System.out.println("stock of books :"+stock);
        }
        else{
            System.out.println("something went wrong");
        }

        preparedStatement = connection.prepareStatement(DbQueries.ALLOCATE_COPY_OF_A_BOOK);
        preparedStatement.setInt(1,stock+1);
        preparedStatement.setInt(2,bookId);
        preparedStatement.execute();

        connection.commit();
    }
}
