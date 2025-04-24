package ru.mephi.lotterydrawservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mephi.lotterydrawservice.dto.response.DrawResponseDto;
import ru.mephi.lotterydrawservice.dto.request.DrawCreateRequestDto;
import ru.mephi.lotterydrawservice.dto.response.DrawCreateResponseDto;
import ru.mephi.lotterydrawservice.dto.response.WinningCombinationResponseDto;
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

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}/results")
    public ResponseEntity<WinningCombinationResponseDto> getWinningCombinationOfTheDraw(@PathVariable long id) {
        return ResponseEntity.ok(drawService.getWinningCombinationOfTheDraw(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<DrawCreateResponseDto> createDraw(@RequestBody DrawCreateRequestDto createRequestDto) {
        return ResponseEntity.ok(drawService.createDraw(createRequestDto));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/active")
    public ResponseEntity<List<DrawResponseDto>> getActiveDraw() {
        return ResponseEntity.ok(drawService.getDrawsByStatus(DrawStatus.ACTIVE));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/cancel/admin")
    public ResponseEntity<Void> cancelDraw(@PathVariable Long id) {
        drawService.cancelDraw(id);
        return ResponseEntity.noContent()
                .build();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/completed")
    public ResponseEntity<List<DrawResponseDto>> getCompletedDraw() {
        return ResponseEntity.ok(drawService.getDrawsByStatus(DrawStatus.COMPLETED));
    }
}
