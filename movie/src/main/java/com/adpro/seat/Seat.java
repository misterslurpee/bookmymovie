package com.adpro.seat;

public class Seat {
    private boolean isBooked;
    int seatNumber;
    public String type;

    public Seat(boolean isBooked, int seatNumber) {
        this.isBooked = isBooked;
        this.seatNumber = seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void booked() {
        this.isBooked = true;
    }

    public void unbooked() {
        this.isBooked = false;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
