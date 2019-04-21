package com.adpro.seat;

import java.util.ArrayList;
import java.util.List;

public class Theatre {
    private int theatreNumber;
    private String description;
    private int seatCount;
    private List<Seat> rows;

    public Theatre(int theatreNumber, String description, int seatCount) {
        this.theatreNumber = theatreNumber;
        this.description = description;
        rows = new ArrayList<Seat>();
        createRows(seatCount);
    }

    public Theatre(List<Seat> rows) {
        this.rows = rows;
    }

    public void createRows(int seatCount) {
        for (int seat = 1; seat <= seatCount; seat++) {
            Seat oneSeat;
            if (seat < (seatCount/6)-1) oneSeat = new CloseSeat(false, seat);
            else if (seat < seatCount*3/6) oneSeat = new MiddleSeat(false, seat);
            else if (seat < (seatCount*4/6)-2) oneSeat = new FarSeat(false, seat);
            else oneSeat = new SideSeat(false, seat);
            addSeatToRow(oneSeat);
        }
        this.seatCount += seatCount;
    }

    public void addSeatToRow(Seat seat) {
        rows.add(seat);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public List<Seat> getRows() {
        return rows;
    }

    public void setRows(List<Seat> rows) {
        this.rows = rows;
    }

    public int getTheatreNumber() {
        return theatreNumber;
    }

    public void setTheatreNumber(int theatreNumber) {
        this.theatreNumber = theatreNumber;
    }
}
