package com.forkfoe.forkfoe.model;

public class Table {
    private int number;
    private int maxSeats;
    private String reservationName;

    public Table(int number, int maxSeats, String reservationName) {
        this.number = number;
        this.maxSeats = maxSeats;
        this.reservationName = reservationName;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public int getMaxSeats() {
        return maxSeats;
    }
    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public String getReservationName() {
        return reservationName;
    }
    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    public boolean isAvailable() {
        return reservationName == null || reservationName.isBlank();
    }

    @Override
    public String toString() {
        return "Table " + number;
    }
}
