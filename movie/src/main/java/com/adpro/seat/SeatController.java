package com.adpro.seat;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeatController {
    private Theatre theatre;

    public SeatController() {
        this.theatre = new Theatre(1, "theatre A", 50);
    }

    @RequestMapping("/seat")
    public List<Seat> seatAPI() {
        return this.theatre.getRows();
    }

}
