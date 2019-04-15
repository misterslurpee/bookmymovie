package com.adpro.ticket.web;

import com.adpro.ticket.Ticket;
import com.adpro.ticket.api.TicketRequestModel;
import com.adpro.ticket.api.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketsController {

    private TicketService ticketService;

    @Autowired
    public TicketsController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    @RequestMapping("/tickets")
    public ResponseEntity<Ticket> tickets(@RequestBody TicketRequestModel requestTicket) {
        return ticketService.orderTicket(requestTicket)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().body(null));
    }

    @PostMapping
    @RequestMapping("/tickets/{ticketId}/verify")
    public ResponseEntity<Ticket> verify(@PathVariable(name = "ticketId") Long ticketId) {
        return ticketService.verifyTicket(ticketId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().body(null));
    }
}
