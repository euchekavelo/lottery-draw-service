package ru.mephi.lotterydrawservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(
    name = "balances",
    indexes = {
        @Index(name = "idx_balance_user_id", columnList = "user_id")
    }
)
@Data
public class Balance {

    @Id
    private Long userId;

    @Column(precision = 12, scale = 2)
    private BigDecimal amount;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
