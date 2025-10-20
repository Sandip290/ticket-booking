package com.ticketbooking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketbooking.entities.User;
import com.ticketbooking.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService{
    private User user;
    private List<User> userList;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String USERS_PATH = "app/src/main/java/com/ticketbooking/localdb/users.json";

    public UserBookingService(User user1) throws IOException{

        this.user = user1;
        loadUsers();
    }

    public UserBookingService() throws IOException{
       loadUsers();
    }

    public List loadUsers()  throws IOException{
        File users = new File(USERS_PATH);
        return objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }

    public boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equalsIgnoreCase(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.findFirst();
        }).findFirst();
        return foundUser.isPresent();
    }

    public boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListFile();
            return Boolean.TRUE;
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }

    private  void saveUserListFile() throws IOException {
            File file = new File(USERS_PATH);
            objectMapper.writeValue(file, userList);
    }
    // json ==> Object(User) deserialize
    // Object(user) ==> json serialize

    public void fetchBooking(){
        user.printTickets();
    }

    public Boolean cancelBooking(String ticketId){
        // how to do it
        //-user is stored in global
        //-then fetch the ticket from user using the ticketId
        //-go into the array of the ticketId and remove the ticket id
        //-then update the localdb after the ticketId is removed from the array
        return Boolean.FALSE;
    }
}

