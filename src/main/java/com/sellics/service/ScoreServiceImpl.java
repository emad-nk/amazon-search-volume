package com.sellics.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sellics.datatransferobject.ScoreDTO;
import com.sellics.domain.AmazonResponse;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScoreServiceImpl implements ScoreService {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(ScoreServiceImpl.class);

    @Value("${amazonURI}")
    private String amazonURI;

    @Override
    public ScoreDTO getScore(String keyword) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(amazonURI + keyword, String.class);
        int score = getNumberOfWords(response) * 10;
        return getScoreDTO(keyword, score);
    }

    private int getNumberOfWords(String response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List list = mapper.readValue(response, List.class);
        String words = list.get(1).toString().replaceAll("[\\[\\]]", "");
        if (words.length() == 0) {
            return 0;
        }
        String[] wordsList = words.split(",");
        return wordsList.length;
    }

    private ScoreDTO getScoreDTO(String keyword, int score) {
        ScoreDTO.ScoreDTOBuilder scoreDTOBuilder = ScoreDTO.newBuilder()
                .setKeyword(keyword)
                .setScore(score);
        return scoreDTOBuilder.createScoreDTO();
    }
}
