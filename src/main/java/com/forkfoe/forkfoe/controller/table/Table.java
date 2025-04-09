package com.forkfoe.forkfoe.controller.table;

public class Table {
    private int number;
    private String reservationName;
    private TableStatus status;
    private int currentOccupants;
    private int maxOccupants;

    public Table(int number, String reservationName, TableStatus status, int currentOccupants, int maxOccupants){
        this.number = number;
        this.reservationName = reservationName;
        this.status = status;
        this.currentOccupants = currentOccupants;
        this.maxOccupants = maxOccupants;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public String getReservationName() {
        return reservationName;
    }
    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    public int getCurrentOccupants() {
        return currentOccupants;
    }
    public void setCurrentOccupants(int currentOccupants) {
        this.currentOccupants = currentOccupants;
    }

    public int getMaxOccupants() {
        return maxOccupants;
    }
    public void setMaxOccupants(int maxOccupants) {
        this.maxOccupants = maxOccupants;
    }

    public TableStatus getStatus() {
        return status;
    }
    public void setStatus(TableStatus status) {
        this.status = status;
    }
}



