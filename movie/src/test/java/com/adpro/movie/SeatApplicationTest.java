package com.adpro.movie;

import antlr.build.Tool;
import com.adpro.seat.Theatre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeatApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getTheatreWithSeat() throws Exception {
        Theatre theatre = new Theatre(1, "A", 50);

        this.mvc.perform(get("/seat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].seatNumber", is(1)))
                .andExpect(jsonPath("$[0].type", is(theatre.getRows().get(0).getType())))
                .andExpect(jsonPath("$[0].booked", is(theatre.getRows().get(0).isBooked())));
    }
}
