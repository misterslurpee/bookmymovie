package com.adpro.seat;

public class MiddleSeat extends Seat{
    private int cost;

    public MiddleSeat(boolean isBooked, int seatNumber) {
        super(isBooked, seatNumber);
        cost = 45000;
        type = "Middle";
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        cost = cost;
    }
}
