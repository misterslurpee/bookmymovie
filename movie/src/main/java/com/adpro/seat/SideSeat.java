package com.adpro.seat;

public class SideSeat extends Seat{
    private int cost;

    public SideSeat(boolean isBooked, int seatNumber) {
        super(isBooked, seatNumber);
        cost = 45000;
        type = "Side";
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        cost = cost;
    }
}
