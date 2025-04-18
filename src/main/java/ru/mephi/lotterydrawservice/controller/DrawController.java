package ru.mephi.lotterydrawservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mephi.lotterydrawservice.dto.response.WinningCombinationResponseDto;
import ru.mephi.lotterydrawservice.exception.DrawResultNotFoundException;
import ru.mephi.lotterydrawservice.service.DrawService;

@RestController
@RequestMapping("/api/draws")
public class DrawController {

    private final DrawService drawService;

    @Autowired
    public DrawController(DrawService drawService) {
        this.drawService = drawService;
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<WinningCombinationResponseDto> getWinningCombinationOfTheDraw(@PathVariable long id)
            throws DrawResultNotFoundException {

        return ResponseEntity.ok(drawService.getWinningCombinationOfTheDraw(id));
    }
}
