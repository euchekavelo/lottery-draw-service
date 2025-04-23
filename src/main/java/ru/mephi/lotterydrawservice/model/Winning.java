package ru.mephi.lotterydrawservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "winnings")
@Data
public class Winning {

    @Id
    private Long ticketId;

    @Column(precision = 12, scale = 2)
    private BigDecimal amount;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}
