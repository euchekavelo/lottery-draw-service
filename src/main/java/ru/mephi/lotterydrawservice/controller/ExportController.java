package ru.mephi.lotterydrawservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.lotterydrawservice.dto.request.PeriodRequestDto;
import ru.mephi.lotterydrawservice.dto.request.WinningRequestDto;
import ru.mephi.lotterydrawservice.dto.response.*;
import ru.mephi.lotterydrawservice.service.ExportService;

@RestController
@RequestMapping("/api/exports")
public class ExportController {

    private final ExportService exportService;

    @Autowired
    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @PostMapping("/winnings")
    public ResponseEntity<ResponseDto> creditWinnings(@RequestBody WinningRequestDto winningRequestDto) {
        return ResponseEntity.ok(exportService.creditWinnings(winningRequestDto));
    }

    @GetMapping("/winners/draws/{drawId}")
    public ResponseEntity<DrawWinnersResponseDto> getInformationAboutWinnersOfDraw(@PathVariable long drawId) {
        return ResponseEntity.ok(exportService.getInformationAboutWinnersOfDraw(drawId));
    }

    @GetMapping("/user-statistics")
    public ResponseEntity<UserStatisticsResponseDto> getUserStatisticsForAPeriod(
            @RequestBody PeriodRequestDto periodRequestDto) {

        return ResponseEntity.ok(exportService.getUserStatisticsForAPeriod(periodRequestDto));
    }

    @GetMapping("/winning-statistics")
    public ResponseEntity<WinningStatisticsResponseDto> getWinningStatisticsForAPeriod(
            @RequestBody PeriodRequestDto periodRequestDto) {

        return ResponseEntity.ok(exportService.getWinningStatisticsForAPeriod(periodRequestDto));
    }

    @GetMapping("/draw-statistics")
    public ResponseEntity<DrawStatisticsResponseDto> getDrawStatisticsForAPeriod(
            @RequestBody PeriodRequestDto periodRequestDto) {

        return ResponseEntity.ok(exportService.getDrawStatisticsForAPeriod(periodRequestDto));
    }
}
