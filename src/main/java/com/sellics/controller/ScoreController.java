package com.sellics.controller;

import com.sellics.datatransferobject.ScoreDTO;
import com.sellics.service.ScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("v1/score")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/{keyword}")
    public ScoreDTO getScore(@PathVariable String keyword) throws IOException {
        return scoreService.getScore(keyword);
    }
}
