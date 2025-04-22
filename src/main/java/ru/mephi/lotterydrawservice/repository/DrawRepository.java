package ru.mephi.lotterydrawservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.lotterydrawservice.model.Draw;
import ru.mephi.lotterydrawservice.model.enums.DrawStatus;

import java.util.List;

@Repository
public interface DrawRepository extends JpaRepository<Draw, Long> {

    List<Draw> findAllByStatus(DrawStatus status);
}
