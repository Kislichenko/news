package com.kislichenko.news.exceptions;

public class ClientNotFoundException extends Exception {
    private long book_id;

    public ClientNotFoundException(long client_id) {
        super(String.format("Client is not found with id : '%s'", client_id));
    }
}
