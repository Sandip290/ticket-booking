package com.ticketbooking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User {
     private String name;
     private String password;
    @JsonProperty("hashed_password")
    private String hashPassword;
     private List<Ticket> ticketsBooked;
     private String userId;

     public User(String name, String password, String hashPassword,  List<Ticket> ticketsBooked, String userId) {
         this.name = name;
          this.password = password;
          this.hashPassword = hashPassword;
          this.ticketsBooked = ticketsBooked;
          this.userId = userId;
     }

     public User() {}

    public String getName() {
         return name;
    }

    public String getPassword() {
         return password;
    }

    public String getHashedPassword() {
         return hashPassword;
    }

    public List<Ticket> getTicketsBooked() {
         return ticketsBooked;
    }

    public void printTickets() {
         if(ticketsBooked == null  || !ticketsBooked.isEmpty()){
             for (int i = 0; i < ticketsBooked.size(); i++) {
                 System.out.println((i + 1) + ". " + ticketsBooked.get(i).getTrainInfo());
             }
             System.out.println("---------------------------");
        }else{
             System.out.println("No tickets is booked for the user");
         }

    }

    public String getUserId() {
         return userId;
    }

    public void setName(String name) {
         this.name = name;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }


//     public void getHashPassword(List<Ticket> ticketsBooked) {
//         this.ticketsBooked = ticketsBooked;
//     }

    public void getTicketsBooked(List<Ticket> ticketsBooked) {
         this.ticketsBooked = ticketsBooked;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public void setTicketsBooked(List<Ticket> ticketsBooked) {
//        this.ticketsBooked = ticketsBooked;
//    }

     public void setUserId(String userId) {
         this.userId = userId;
     }

}
