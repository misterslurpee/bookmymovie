package com.adpro.ticket;

import com.adpro.ticket.api.TicketRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TicketApplicationTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void testCanOrderSeat() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new TicketRequestModel(1L, "1B"));
        this.mvc.perform(post("/tickets").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("$.status", is("PENDING")))
                .andExpect(status().isOk());
    }

    @Test
    public void testCannotOrderBookedSeat() throws Exception {
        ticketRepository.save(new Ticket(1L, "1A", Ticket.Status.VERIFIED));
        String json = new ObjectMapper().writeValueAsString(new TicketRequestModel(1L, "1A"));
        this.mvc.perform(post("/tickets").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCanVerifyTicket() throws Exception {
        Ticket ticket = new Ticket(1L, "1A", Ticket.Status.PENDING);
        ticketRepository.save(ticket);
        this.mvc.perform(post("/tickets/" + ticket.getId() + "/verify"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("VERIFIED")));
    }

    @Test
    public void testCancelledTicketNotVerified() throws Exception {
        Ticket ticket = new Ticket(4L, "1K", Ticket.Status.CANCELLED);
        ticketRepository.save(ticket);
        this.mvc.perform(post("/tickets/" + ticket.getId() + "/verify"))
                .andExpect(jsonPath("$.status", is("CANCELLED")));
    }

    @Test
    public void testVerifyInvalidTicket() throws Exception {
        this.mvc.perform(post("/tickets/12321/verify"))
                .andExpect(status().is4xxClientError());
    }
}