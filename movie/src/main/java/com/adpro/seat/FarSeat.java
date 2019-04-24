package com.adpro.seat;

public class FarSeat extends Seat{
    private static int cost;

    public FarSeat(boolean isBooked, int seatNumber) {
        super(isBooked, seatNumber);
        cost = 40000;
        type = "Far";
    }

    public static int getCost() {
        return cost;
    }

    public static void setCost(int cost) {
        cost = cost;
    }

}
