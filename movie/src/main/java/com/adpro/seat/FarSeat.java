package com.adpro.seat;

public class FarSeat extends Seat{
    private int cost;

    public FarSeat(boolean isBooked, int seatNumber) {
        super(isBooked, seatNumber);
        cost = 35000;
        type = "Far";
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        cost = cost;
    }
}
