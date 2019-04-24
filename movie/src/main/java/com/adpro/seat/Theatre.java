package com.adpro.seat;

import java.util.ArrayList;
import java.util.List;

public class Theatre {

    private Integer theatreNumber;

    private String description;

    private int seatCount;

    private List<Seat> rows;

    private static List<Theatre> theatres = new ArrayList<Theatre>();

    public Theatre(int theatreNumber, String description, int seatCount) {
        this.theatreNumber = theatreNumber;
        this.description = description;
        this.rows = new ArrayList<Seat>();
        this.seatCount = seatCount;
    }

    public Theatre(Theatre newTheatres) {
        theatres.add(newTheatres);
    }

    public static void addingNewTheatreToList(Theatre newTheatre) {
        theatres.add(newTheatre);
    }

    public void createRows() {
        for (int seatNum = 1; seatNum <= seatCount; seatNum++) {
            Seat oneSeat;
            if (seatNum < seatCount*2/3) oneSeat = new MiddleSeat(false, seatNum);
            else oneSeat = new FarSeat(false, seatNum);
            addSeatToRow(oneSeat);
        }
    }

    public void addSeatToRow(Seat seat) {
        rows.add(seat);
    }

    public String getDescription() {
        return description;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public List<Seat> getRows() {
        return rows;
    }

    public int getTheatreNumber() {
        return theatreNumber;
    }

    public static List<Theatre> getTheatres() {
        return theatres;
    }
}
