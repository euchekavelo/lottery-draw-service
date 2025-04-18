package ru.mephi.lotterydrawservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import ru.mephi.lotterydrawservice.model.enums.DrawStatus;
import ru.mephi.lotterydrawservice.model.enums.LotteryType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "draws")
@Data
public class Draw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private LotteryType lotteryType;

    private LocalDateTime startTime;

    private LocalDateTime finishTime;

    @Enumerated(EnumType.STRING)
    private DrawStatus status;

    @OneToMany(mappedBy = "draw", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Ticket> ticketList = new ArrayList<>();

    @OneToOne(mappedBy = "draw", cascade = CascadeType.ALL)
    private DrawResult drawResult;
}
