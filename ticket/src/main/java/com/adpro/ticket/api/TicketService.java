package com.adpro.ticket.api;

import com.adpro.ticket.Ticket;

import java.util.Optional;

public interface TicketService {
    boolean canOrderTicket(TicketRequestModel r);
    Optional<Ticket> orderTicket(TicketRequestModel r);
    Optional<Ticket> verifyTicket(Long ticketId);
}
