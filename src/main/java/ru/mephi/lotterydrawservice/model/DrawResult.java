package ru.mephi.lotterydrawservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "draw_results")
@Data
public class DrawResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "draw_id")
    private Draw draw;

    private String winningCombination;

    @CreationTimestamp
    private LocalDateTime resultTime;
}
