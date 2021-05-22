package com.lms;

import com.lms.LmsServiceIml;
import com.lms.exceptions.ExceptionHandler;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class SimpleGrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
                        .intercept(new ExceptionHandler())
                        .addService(new LmsServiceIml())
                        .build();
        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }
}
