package com.adpro.seat;

public class CloseSeat extends Seat{
    private int cost;

    public CloseSeat(boolean isBooked, int seatNumber) {
        super(isBooked, seatNumber);
        cost = 30000;
        type = "Close";
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        cost = cost;
    }
}
