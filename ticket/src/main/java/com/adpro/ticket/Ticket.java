package com.adpro.ticket;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long sessionId;
    private String seatId;
    private Status status;

    public enum Status {
        PENDING, VERIFIED, CANCELLED
    }

    public Ticket(Long sessionId, String seatId, Status status) {
        this.sessionId = sessionId;
        this.seatId = seatId;
        this.status = status;
    }
}
