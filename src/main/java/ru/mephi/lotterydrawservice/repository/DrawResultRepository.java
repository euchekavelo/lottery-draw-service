package ru.mephi.lotterydrawservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mephi.lotterydrawservice.model.DrawResult;

import java.util.Optional;

@Repository
public interface DrawResultRepository extends JpaRepository<DrawResult, Long> {

    Optional<DrawResult> findByDrawId(long drawId);
}
