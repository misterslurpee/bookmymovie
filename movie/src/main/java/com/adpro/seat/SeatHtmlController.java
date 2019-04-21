package com.adpro.seat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SeatHtmlController {
    @GetMapping("/showing-seat")
    public String showSeat(@RequestParam(name = "name", required=false) String name, Model model) {
        return "show-seat";
    }
}
