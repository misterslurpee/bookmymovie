package com.adpro.ticket.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TicketRequestModel {
    private Long sessionId;
    private String seatId;
}
