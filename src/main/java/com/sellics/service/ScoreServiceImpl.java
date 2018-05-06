package com.sellics.service;

import com.sellics.datatransferobject.ScoreDTO;
import com.sellics.exception.AmazonApiException;
import com.sellics.exception.MapperException;
import com.sellics.util.SearchVolumeUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(ScoreServiceImpl.class);

    @Value("${amazonURI}")
    private String amazonURI;

    @Override
    public ScoreDTO getScore(String keyword) throws AmazonApiException, MapperException {
        LOG.debug("Keyword: {}", keyword);
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder tempKeyword = new StringBuilder();
        List<String> mappedResponse = null;
        char[] keywordChars = keyword.toLowerCase().toCharArray();

        try {
            for (char keywordChar : keywordChars) {
                tempKeyword.append(keywordChar);
                String response = restTemplate.getForObject(amazonURI + tempKeyword, String.class);
                mappedResponse = SearchVolumeUtil.getMappedResponseList(response);
                if (SearchVolumeUtil.keywordIsFoundInResponse(mappedResponse, keyword))
                    break;
            }
        } catch (MapperException e) {
            throw new MapperException("Mapper could not map the returned json into a list from amazon API");
        } catch (Exception e) {
            throw new AmazonApiException("Amazon Api failed to respond");
        }
        LOG.debug("mappedResponseSize: {}", mappedResponse.size());
        int score = SearchVolumeUtil.getEstimatedScore(keyword.length(), tempKeyword.length(), mappedResponse.size());

        return getScoreDTO(keyword, score);
    }

    private ScoreDTO getScoreDTO(String keyword, int score) {
        ScoreDTO.ScoreDTOBuilder scoreDTOBuilder = ScoreDTO.newBuilder()
                .setKeyword(keyword)
                .setScore(score);
        return scoreDTOBuilder.createScoreDTO();
    }
}
