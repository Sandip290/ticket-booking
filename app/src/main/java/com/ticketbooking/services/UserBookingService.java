package com.ticketbooking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketbooking.entities.Ticket;
import com.ticketbooking.entities.Train;
import com.ticketbooking.entities.User;
import com.ticketbooking.utils.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class UserBookingService{
    private User user;
    private List<User> userList;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String USERS_PATH = "D:\\web development\\Projects\\IRCTC\\app\\src\\main\\java\\com\\ticketbooking\\localdb\\users.json";

    public UserBookingService(User user1) throws IOException{
        this.user = user1;
        this.userList = loadUsers();
    }

    public User getUser() {
        return user;
    }
    public UserBookingService() throws IOException{
       this.userList = loadUsers();
    }

    public List<User> loadUsers()  throws IOException{
        File users = new File(USERS_PATH);
        if(!users.exists() || users.length() == 0){
            return new ArrayList<>();
        }
        return objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }

    public boolean loginUser(){
        Optional<User> foundUser = this.userList.stream().filter((user1) ->
                user1.getName().equals(this.user.getName()) &&
                        UserServiceUtil.checkPassword(this.user.getPassword(), user1.getHashedPassword())).findFirst();
        if(foundUser.isPresent()){
            this.user = foundUser.get();
        }
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

    public void fetchBookings(){
        if(this.user != null){
            this.user.printTickets();
        }else{
            System.out.println("user is not logged in: ");
        }

    }

    public List<List<Integer>> fetchSeats (Train train){
        if(train != null){
            return train.getSeats();
        }
        return null;
    }

    public Boolean bookTrainSeat(Train train, int row, int col, String source, String destination) {
        if (this.user == null || train == null) {
            System.out.println("Error: User not logged in or invalid train selected.");
            return Boolean.FALSE;
        }

        try {
            // 1. Validation: Check if the seat is available (0=available, 1=booked)
            if (train.getSeats().get(row).get(col) == 1) {
                System.out.println("Seat is already booked.");
                return Boolean.FALSE;
            }

            // 2. Update Train State: Mark the seat as booked
            train.getSeats().get(row).set(col, 1);

            // 3. Persist Train Data (Requires TrainService to save the master list)
            // We assume TrainService has a loadTrains() and saveTrainList() method.
            TrainService trainService = new TrainService();

            // Update the specific train in the master train list
            List<Train> masterTrainsList = trainService.loadTrains().stream()
                    .map(t -> t.getTrainId().equals(train.getTrainId()) ? train : t)
                    .collect(Collectors.toList());

            // Save the updated master list back to trains.json
            trainService.saveTrainsList(masterTrainsList);

            // 4. Create New Ticket
            String newTicketId = UUID.randomUUID().toString();
            Ticket newTicket = new Ticket(
                    newTicketId,
                    this.user.getUserId(),
                    source,
                    destination,
                    new Date(),
                    train
            );

            // 5. Update User State: Add the ticket to the current user's booked list
            this.user.getTicketsBooked().add(newTicket);

            // 6. Persist User Data (Save the master user list)
            // Replace the old user data with the updated 'this.user' object
            List<User> updatedUserList = this.userList.stream()
                    .map(u -> u.getUserId().equals(this.user.getUserId()) ? this.user : u)
                    .collect(Collectors.toList());

            this.userList = updatedUserList;
            saveUserListFile(); // Saves the master list to users.json

            System.out.println("Ticket booked successfully! Ticket ID: " + newTicketId);
            return Boolean.TRUE;

        } catch (Exception ex) {
            System.out.println("An error occurred during booking: " + ex.getMessage());
            return Boolean.FALSE;
        }
    }

    public Boolean trainSelectedForBooking (Train train, int row, int col, String source, String destination){
        if(this.user == null || train == null){
            System.out.println("Error user is not logged in or ivalid train selected");
            return Boolean.FALSE;
        }
        try{
            //1.check if seats are available 0 ==> available and 1 ==> unavailable
            if(train.getSeats().get(row).get(col) == 1){
                System.out.println("this seat is already Booked");
                return Boolean.FALSE;
            }

            //2. book the seat on the train
//            train.getSeats().get(row).set(col, train.getSeats().get(row).get(col));
            train.getSeats().get(row).set(col, 1);

            // 3. Persist the updated train data
            TrainService trainService = new TrainService();
            // Get the master list, update the one train, and save the list
            List<Train> masterTrainList = trainService.loadTrains().stream()
                    .map(t -> t.getTrainId().equals(train.getTrainId()) ? train : t)
                    .collect(Collectors.toList());
            trainService.saveTrainsList(masterTrainList);

            String newTicketId = UUID.randomUUID().toString();
            Ticket newTicket = new Ticket(
                    newTicketId,
                    this.user.getUserId(),
                    source,
                    destination,
                    new Date(),
                    train
            );
            // 5. Add ticket to the current user's booked list
            if(this.user.getTicketsBooked()== null){
                this.user.getTicketsBooked(new ArrayList<>()) ;
            }
                this.user.getTicketsBooked().add(newTicket);

            //6. persist the updated user data (save the master user list)
            List <User> updatedUserList = this.userList.stream().map(u -> u.getUserId()
                    .equals(this.user.getUserId()) ? this.user : u ).collect(Collectors.toList());
            this.userList = updatedUserList;
            saveUserListFile();

            System.out.println("ticket booked successfully! Ticket ID:" + newTicketId);
            return Boolean.TRUE;

        }catch(Exception ex){
            System.out.println("Error occured during the booking process" + ex.getMessage());
            return Boolean.FALSE;
        }
    }

    public Boolean cancelBooking(String ticketId){
        // how to do it
        //-user is stored in global
        //-then fetch the ticket from user using the ticketId
        //-go into the array of the ticketId and remove the ticket id
        //-then update the localdb after the ticketId is removed from the array
        if(this.user == null || this.user.getTicketsBooked() == null ){
            return Boolean.FALSE;
        }

        boolean wasRemoved = this.user.getTicketsBooked().removeIf(ticket ->  ticket.getTicketId().equals(ticketId));
        if(wasRemoved){
            try{
                List<User> updatedUserList = this.userList.stream()
                        .map(u -> u.getUserId().equals(this.user.getUserId()) ? this.user : u)
                        .collect(Collectors.toList());
                this.userList =  updatedUserList;
                saveUserListFile();
                return Boolean.TRUE;
            }
           catch(IOException exception){
                return Boolean.FALSE;
            }
        }
        return Boolean.FALSE;
    }


    public List<Train> getTrains(String source , String destination){
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrain(source, destination);
        }catch (IOException ex){
            // Should log the exception in a real app
            return new ArrayList<>();
        }

    }
}
