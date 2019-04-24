package com.adpro.seat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SeatController {
    public SeatController() {
        Theatre theatre1 = new Theatre(1, "A",50);
        theatre1.createRows();
        Theatre.addingNewTheatreToList(theatre1);
    }

    @GetMapping("/seat")
    public @ResponseBody List<Theatre> seatAPI() {
        return Theatre.getTheatres();
    }

    @RequestMapping("/showing+seat")
    public String showSeat(Model model) {
        model.addAttribute("theatre", "CGV");
        return "show-seat";
    }

}
