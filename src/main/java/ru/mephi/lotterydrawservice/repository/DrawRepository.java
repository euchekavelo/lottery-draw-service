package ru.mephi.lotterydrawservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.lotterydrawservice.model.Draw;

@Repository
public interface DrawRepository extends JpaRepository<Draw, Long> {
}
