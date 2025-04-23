package ru.mephi.lotterydrawservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mephi.lotterydrawservice.model.Draw;
import ru.mephi.lotterydrawservice.model.enums.DrawStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DrawRepository extends JpaRepository<Draw, Long> {

    List<Draw> findAllByStatus(DrawStatus status);

    @Query(value = "select \n" +
            "\tCOUNT(*) as count\n" +
            "from \n" +
            "\tdraws d \n" +
            "where \n" +
            "\td.start_time >= ?1\n" +
            "\tand d.finish_time <= ?2", nativeQuery = true)
    int getCountAllByPeriodBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
