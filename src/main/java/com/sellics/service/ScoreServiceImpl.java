package com.sellics.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sellics.datatransferobject.ScoreDTO;
import com.sellics.exception.AmazonApiException;
import com.sellics.exception.MapperException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(ScoreServiceImpl.class);

    @Value("${amazonURI}")
    private String amazonURI;

    @Override
    public ScoreDTO getScore(String keyword) {
        LOG.debug("Keyword: {}", keyword);
        RestTemplate restTemplate = new RestTemplate();
        String response = null;
        try {
            response = restTemplate.getForObject(amazonURI + keyword, String.class);
        } catch (Exception e) {
            LOG.debug("rest template exception: {}", e.getMessage());
            throw new AmazonApiException("Amazon Api fialed to respond");
        }

        //multiply by 10 to get the score based on 0 to 100
        int score = getNumberOfWords(response) * 10;

        return getScoreDTO(keyword, score);
    }

    private int getNumberOfWords(String response) {
        ObjectMapper mapper = new ObjectMapper();
        //it's a raw json so it's easier to put the data in a list
        List list = null;
        try {
            list = mapper.readValue(response, List.class);
        } catch (IOException e) {
            LOG.debug("mapper exception: {}", e.getMessage());
            throw new MapperException("Mapper could not map the returned json into a list from amazon API");
        }
        //remove the [ and ] from the string
        String words = list.get(1).toString().replaceAll("[\\[\\]]", "");
        LOG.debug("words: {}", words);
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
