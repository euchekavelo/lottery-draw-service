package ru.mephi.lotterydrawservice.scheduler;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mephi.lotterydrawservice.model.enums.DrawStatus;
import ru.mephi.lotterydrawservice.repository.DrawRepository;
import ru.mephi.lotterydrawservice.service.DrawResultService;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class DrawScheduler {

    private final DrawRepository drawRepository;
    private final DrawResultService resultService;

    @Scheduled(cron = "0 */1 * * * *")
    public void updateDrawWhenStatusPlanned() {
        drawRepository.findAllByStatus(DrawStatus.PLANNED)
                .stream()
                .filter(draw -> draw.getStartTime() != null && draw.getStartTime().isBefore(LocalDateTime.now()))
                .forEach(draw -> {
                    draw.setStatus(DrawStatus.ACTIVE);
                    drawRepository.save(draw);
                });
        drawRepository.findAllByStatus(DrawStatus.ACTIVE)
                .stream()
                .filter(draw -> draw.getFinishTime() != null && draw.getFinishTime().isBefore(LocalDateTime.now()))
                .forEach(draw -> {
                    draw.setStatus(DrawStatus.COMPLETED);
                    Hibernate.initialize(draw.getTicketList());
                    resultService.defineDrawResults(draw);
                    drawRepository.save(draw);
                });
    }
}
