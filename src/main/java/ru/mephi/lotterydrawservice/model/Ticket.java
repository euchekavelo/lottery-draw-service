package ru.mephi.lotterydrawservice.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.mephi.lotterydrawservice.model.enums.TicketStatus;

@Entity
@Table(
    name = "tickets",
    indexes = {
        @Index(name = "idx_ticket_user_id", columnList = "user_id"),
        @Index(name = "idx_ticket_draw_id", columnList = "draw_id"),
        @Index(name = "idx_ticket_status", columnList = "status")
    }
)
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

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    private Winning winning;
}
