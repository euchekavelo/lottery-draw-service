package ru.mephi.lotterydrawservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.lotterydrawservice.model.DrawResult;

@Repository
public interface DrawResultRepository extends JpaRepository<DrawResult, Long> {
}
