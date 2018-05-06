package com.sellics.service;

import com.sellics.datatransferobject.ScoreDTO;
import com.sellics.exception.AmazonApiException;
import com.sellics.exception.MapperException;

import java.io.IOException;

public interface ScoreService {
    ScoreDTO getScore(String keyword) throws IOException, AmazonApiException, MapperException;
}
