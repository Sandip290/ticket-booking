package com.ticketbooking.services;

import com.fasterxml.jackson.core.type.TypeReference; // Added import
import com.fasterxml.jackson.databind.ObjectMapper; // Added import
import com.ticketbooking.entities.Train;

import java.io.File; // Added import
import java.io.IOException; // Added import
import java.util.ArrayList; // Added import
import java.util.List;
import java.util.stream.Collectors;




public class TrainService {

    private Train train;
    private List<Train> trainList;
//    private ObjectMapper objectMapper = new ObjectMapper();
    private ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.findAndRegisterModules();
        objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    private static final String TRAINS_PATH = "app/src/main/java/com/ticketbooking/localdb/trains.json";

//    public TrainService(Train train1) throws IOException{
//        this.train = train1;
//        loadTrains();
//    }

    public TrainService() throws IOException{
        loadTrains();
    }

    public List<Train> loadTrains()  throws IOException {
        File trainsFile = new File(TRAINS_PATH);
        if(!trainsFile.exists() || trainsFile.length() == 0){
            this.trainList = new ArrayList<>();
        }
        else{
//            this.train = objectMapper.readValue(train, new TypeReference<List<Train>>() );
            this.trainList = objectMapper.readValue(trainsFile, new TypeReference<List<Train>>() {});
        }
        return this.trainList;
    }

    public void saveTrainsList(List<Train> trainList) throws IOException {
        File file = new File(TRAINS_PATH);
        objectMapper.writeValue(file, trainList);
    }

    public List<Train> searchTrain(String source, String destination) {
        return trainList.stream().filter(train -> validTrain(train, source, destination)).collect(Collectors.toList());
    }

    public boolean validTrain(Train train, String source, String destination) {
        List<String> stationsOrder = train.getStations();

        int sourceIndex = stationsOrder.indexOf(source);
        int destinationIndex = stationsOrder.indexOf(destination);
        return sourceIndex != 1 && destinationIndex != 1 && sourceIndex < destinationIndex;
    }

}
