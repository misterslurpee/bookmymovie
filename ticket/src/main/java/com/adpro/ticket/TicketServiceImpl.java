package com.adpro.ticket;

import com.adpro.ticket.api.TicketRequestModel;
import com.adpro.ticket.api.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    private TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public boolean canOrderTicket(TicketRequestModel r) {
        return ticketRepository.findOneBySessionIdAndSeatIdAndStatus(r.getSessionId(), r.getSeatId(), Ticket.Status.VERIFIED) == null;
    }

    @Override
    public Optional<Ticket> orderTicket(TicketRequestModel r) {
        if (canOrderTicket(r)) {
            return Optional.of(ticketRepository.save(new Ticket(r.getSessionId(), r.getSeatId(), Ticket.Status.PENDING)));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Ticket> verifyTicket(Long ticketId) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (!ticketOptional.isPresent()) {
            return Optional.empty();
        }
        Ticket ticket = ticketOptional.get();

        if (ticket.getStatus() == Ticket.Status.PENDING) {
            ticket.setStatus(Ticket.Status.VERIFIED);
            return Optional.of(ticketRepository.save(ticket));
        }

        return Optional.of(ticket);
    }
}
