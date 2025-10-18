package com.ticketbooking.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketbooking.entities.User;

import java.io.File;
import java.util.List;

public class UserBookingService {
    private User user;
    private List<User> userList;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String USERS_PATH = "../localdb/users.json";

    public UserBookingService(User user1){

        this.user = user1;
        File users = new File(USERS_PATH);
    }
}
