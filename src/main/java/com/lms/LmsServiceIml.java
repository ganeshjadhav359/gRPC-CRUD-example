package com.lms;

import com.lms.model.Book;
import com.lms.model.User;
import com.lmsService.grpc.*;
import com.lms.configs.CustomValidatorClass;
import com.lms.configs.LmsDbOperations;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.StreamObserver;


import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class LmsServiceIml extends LmsServiceGrpc.LmsServiceImplBase {
    public static final Logger logger = Logger.getLogger(LmsServiceIml.class.getName());
    @Override
    public void addBook(BookRequest request, StreamObserver<BookResponse> responseObserver) {
        Book book = new Book();
        book.setName(request.getBook().getName());
        book.setGenre(request.getBook().getGenre());
        book.setTitle(request.getBook().getTitle());
        book.setStock(request.getBook().getStock());
        logger.info("before doing db operation "+book.getTitle());
        ErrorResponse errorResponse =CustomValidatorClass.validate(book);
        if( errorResponse!=null){
            Metadata metadata = new Metadata();
            Metadata.Key<ErrorResponse> responseKey = ProtoUtils.keyForProto(ErrorResponse.getDefaultInstance());
            metadata.put(responseKey,errorResponse);
            responseObserver.onError(Status.OUT_OF_RANGE.asException(metadata));
            return;
        }

        try {
            LmsDbOperations.addBook(book);
        } catch (SQLException exception) {

            logger.severe(exception.toString());

             throw new RuntimeException(exception.toString());
        }

        responseObserver.onNext(BookResponse.newBuilder().setResult("successfully added book").setName(book.getName()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void registerUser(UserDetailsRequest request, StreamObserver<registerUserResponse> responseObserver) {
        User user = new User();
            user.setEmail(request.getEmail());
            user.setAddress(request.getAddress());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPassword(request.getPassword());
        ErrorResponse errorResponse =CustomValidatorClass.validate(user);
        if( errorResponse!=null){
            Metadata metadata = new Metadata();
            Metadata.Key<ErrorResponse> responseKey = ProtoUtils.keyForProto(ErrorResponse.getDefaultInstance());
            metadata.put(responseKey,errorResponse);
            responseObserver.onError(Status.OUT_OF_RANGE.asException(metadata));
            return;
        }

        try {
            LmsDbOperations.addUser(user);
        } catch (SQLException exception) {
            logger.severe(exception.toString());

            throw new RuntimeException(exception.toString());
        }

        responseObserver.onNext(registerUserResponse.newBuilder().setResult("successfully added user").setEmail(request.getEmail()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllBooks(getAllBooksRequest request, StreamObserver<getAllBooksResponse> responseObserver) {
        try {
           List<com.lmsService.grpc.Book> bookList =  LmsDbOperations.getAllBooks();
            responseObserver.onNext(getAllBooksResponse.newBuilder()
                    .addAllBook(bookList)
                    .build());
            responseObserver.onCompleted();
        } catch (SQLException exception) {
            logger.severe(exception.toString());

            throw new RuntimeException(exception.toString());
        }
    }

    @Override
    public void issueBook(issueBookRequest request, StreamObserver<issueBookResponse> responseObserver) {

        try {
            LmsDbOperations.issueBook(request.getBookId(),request.getUserId());
        }catch (SQLException exception) {
            logger.severe(exception.toString());
            throw new RuntimeException(exception.toString());
        }

        responseObserver.onNext(issueBookResponse.newBuilder()
                .setResult("book issued successfully")
                .build());
        responseObserver.onCompleted();

    }

    @Override
    public void returnBook(returnBookRequest request, StreamObserver<returnBookResponse> responseObserver) {
        try {
            LmsDbOperations.returnBook(request.getBookId(),request.getUserId());
        }catch (SQLException exception) {
            logger.severe(exception.toString());
            throw new RuntimeException(exception.toString());
        }

        responseObserver.onNext(returnBookResponse.newBuilder()
                .setResult("book returned successfully")
                .build());
        responseObserver.onCompleted();
    }
}
