package com.ticketbooking.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Train {

    @JsonProperty("train_id")
    private String trainId;

    @JsonProperty("train_no")
    private String trainNo;

    private List<List<Integer>> seats;

    @JsonProperty("station_times")
    private Map<String, String> stationTimes;

    private List<String> stations;

    public Train() {
        // Required for Jackson deserialization
    }


    public Train(String trainId, List<List<Integer>> seats, String trainNo, Map<String, String> stationTimes, List<String> stations) {
        this.trainId = trainId;
        this.seats = seats;
        this.trainNo = trainNo;
        this.stationTimes = stationTimes;
        this.stations = stations;
    }


    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Integer>> seats) {
        this.seats = seats;
    }

    public Map<String, String> getStationTimes() {
        return stationTimes;
    }

    public void setStationTimes(Map<String, String> stationTimes) {
        this.stationTimes = stationTimes;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }

    public String getTrainInfo(){
        return String.format("Train ID: %s Train No: %s", trainId, trainNo);
    }

}
