package com.forkfoe.forkfoe.model;

public record Table(int number, int maxSeats, String reservationName) {

    @Override
    public String toString() {
        return "Table " + number;
    }
}

