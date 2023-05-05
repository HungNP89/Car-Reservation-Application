package com.example.carreservationapplication.models;

import java.io.Serializable;

public class CarReservedModel implements Serializable {
    String currentTime;
    String currentDate;
    String carName;
    int carPrice;
    String endDate;
    String startDate;
    String documentID;
    int totalPrice;
    int totalDays;

    public CarReservedModel(String currentTime, String currentDate, String carName, int carPrice, String endDate, String startDate, int totalPrice, int totalDays) {
        this.currentTime = currentTime;
        this.currentDate = currentDate;
        this.carName = carName;
        this.carPrice = carPrice;
        this.endDate = endDate;
        this.startDate = startDate;
        this.totalPrice = totalPrice;
        this.totalDays = totalDays;
    }

    public CarReservedModel() {

    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(int carPrice) {
        this.carPrice = carPrice;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDocumentId() {
        return documentID;
    }

    public void setDocumentId(String documentId) {
        this.documentID = documentId;
    }

}
