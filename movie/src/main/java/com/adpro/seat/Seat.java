package com.adpro.seat;

public class Seat {
    private Integer seatNumber;

    private boolean isBooked;

    public String type;

    public Seat(boolean isBooked, int seatNumber) {
        this.isBooked = isBooked;
        this.seatNumber = seatNumber;
    }

    public Integer getSeatNumber() { return this.seatNumber;}

    public boolean isBooked() {
        return isBooked;
    }

    public void booked() {
        this.isBooked = true;
    }

    public void unbooked() {
        this.isBooked = false;
    }

    public String getType() {return this.type;}
}
