package ru.mephi.lotterydrawservice.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.mephi.lotterydrawservice.model.enums.TicketStatus;

@Entity
@Table(name = "tickets")
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "draw_id")
    private Draw draw;

    private String data;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}
