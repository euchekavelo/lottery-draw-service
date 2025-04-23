package ru.mephi.lotterydrawservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mephi.lotterydrawservice.model.User;
import ru.mephi.lotterydrawservice.projection.UserStatisticProjection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    @Query(value = "select\n" +
            "\tu.id as user_id,\n" +
            "\tCOALESCE(SUM(w.amount), 0) as total_winnings,\n" +
            "\tCOALESCE(COUNT(t.id), 0) as winningTicketsCount\n" +
            "from \n" +
            "\tusers u \n" +
            "\tleft join tickets t on u.id = t.user_id \n" +
            "\tleft join draws d on t.draw_id = d.id \n" +
            "\tleft join winnings w on t.id = w.ticket_id\n" +
            "where \n" +
            "\td.start_time >= ?1\n" +
            "\t and d.finish_time <= ?2\n" +
            "\t and t.status = 'WIN'\n" +
            "group by \n" +
            "\tu.id ", nativeQuery = true)
    List<UserStatisticProjection> getUserStatisticsByPeriodBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
