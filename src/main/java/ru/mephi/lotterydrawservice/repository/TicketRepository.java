package ru.mephi.lotterydrawservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.lotterydrawservice.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
