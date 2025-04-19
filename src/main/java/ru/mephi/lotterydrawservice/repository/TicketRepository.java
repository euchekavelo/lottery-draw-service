package ru.mephi.lotterydrawservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.lotterydrawservice.model.Ticket;
import ru.mephi.lotterydrawservice.model.User;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByIdAndUser(Long id, User user);
}
