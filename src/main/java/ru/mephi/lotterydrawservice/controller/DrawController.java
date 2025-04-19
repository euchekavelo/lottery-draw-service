package ru.mephi.lotterydrawservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mephi.lotterydrawservice.dto.DrawDto;
import ru.mephi.lotterydrawservice.dto.request.DrawCreateRequestDto;
import ru.mephi.lotterydrawservice.dto.response.DrawCreateResponseDto;
import ru.mephi.lotterydrawservice.dto.response.WinningCombinationResponseDto;
import ru.mephi.lotterydrawservice.exception.DrawResultNotFoundException;
import ru.mephi.lotterydrawservice.model.enums.DrawStatus;
import ru.mephi.lotterydrawservice.service.DrawService;

import java.util.List;

@RestController
@RequestMapping("/api/draws")
public class DrawController {

    private final DrawService drawService;

    @Autowired
    public DrawController(DrawService drawService) {
        this.drawService = drawService;
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<WinningCombinationResponseDto> getWinningCombinationOfTheDraw(@PathVariable long id) {
        return ResponseEntity.ok(drawService.getWinningCombinationOfTheDraw(id));
    }

    @PostMapping("/admin")
    // todo admin role
    public ResponseEntity<DrawCreateResponseDto> createDraw(@RequestBody DrawCreateRequestDto createRequestDto) {
        return ResponseEntity.ok(drawService.createDraw(createRequestDto));
    }

    @GetMapping("/active")
    public ResponseEntity<List<DrawDto>> getActiveDraw() {
        return ResponseEntity.ok(drawService.getDrawByStatus(DrawStatus.ACTIVE));
    }

    @PutMapping("/{id}/cancel/admin")
    // todo admin role
    public ResponseEntity<Void> cancelDraw(@PathVariable Long id) {
        drawService.cancelDraw(id);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/completed")
    public ResponseEntity<List<DrawDto>> getCompletedDraw() {
        return ResponseEntity.ok(drawService.getDrawByStatus(DrawStatus.COMPLETED));
    }
}
