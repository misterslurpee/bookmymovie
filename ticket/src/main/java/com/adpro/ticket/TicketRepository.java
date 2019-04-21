package com.adpro.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findOneBySessionIdAndSeatIdAndStatus(Long sessionId, String seatId, Ticket.Status status);
}
