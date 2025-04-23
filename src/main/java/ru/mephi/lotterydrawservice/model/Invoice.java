package ru.mephi.lotterydrawservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import ru.mephi.lotterydrawservice.model.enums.InvoiceStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "invoices",
    indexes = {
        @Index(name = "idx_invoice_status", columnList = "status")
    }
)
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String ticketData;

    @CreationTimestamp
    private LocalDateTime registerTime;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonBackReference
    private List<Payment> paymentList = new ArrayList<>();
}
