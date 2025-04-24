package ru.mephi.lotterydrawservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mephi.lotterydrawservice.model.Winning;
import ru.mephi.lotterydrawservice.projection.WinningStatisticProjection;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface WinningRepository extends JpaRepository<Winning, Long> {

    @Query(value = "select \n" +
            "\tCOALESCE(COUNT(*), 0) as number_winnings,\n" +
            "\tCOALESCE(SUM(w.amount), 0) as total_amount\n" +
            "from \n" +
            "\twinnings w\n" +
            "\tinner join tickets t on w.ticket_id = t.id \n" +
            "\tinner join draws d on t.draw_id = d.id \n" +
            "where\n" +
            "\td.start_time >= ?1\n" +
            "\tand d.finish_time <= ?2", nativeQuery = true)
    Optional<WinningStatisticProjection> getWinningStatisticsByPeriodBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
