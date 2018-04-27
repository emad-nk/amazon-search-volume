package com.sellics.service;

import com.sellics.datatransferobject.ScoreDTO;

import java.io.IOException;

public interface ScoreService {
    ScoreDTO getScore(String keyword) throws IOException;
}
