package com.sellics.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sellics.exception.MapperException;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public final class SearchVolumeUtil {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(SearchVolumeUtil.class);
    private static final int MAX_SCORE = 100;
    private static final double MAX_AMAZON_RESPONSE = 10;

    public static boolean keywordIsFoundInResponse(List<String> mappedResponse, String keyword) {
        return mappedResponse.stream().anyMatch(str -> str.trim().equals(keyword.toLowerCase()));
    }

    public static int getEstimatedScore(int keywordLength, int keywordFoundAtChar, int amazonResponseSize) {
        LOG.debug("keywordLength: {}, keywordFoundAtChar: {}, amazonResponseSize: {}", keywordLength, keywordFoundAtChar, amazonResponseSize);

        double responseSizeOutOfOne = amazonResponseSize / MAX_AMAZON_RESPONSE; // to get 0-1, 1 is the maximum response from amazon (i.e 10/10 = 1)
        if (keywordFoundAtChar == 1) {
            return MAX_SCORE;
        }
        int score = MAX_SCORE - ((keywordFoundAtChar * MAX_SCORE) / keywordLength);
        score *= responseSizeOutOfOne; // based on how many words are returned (if 9 words are returned multiply by 0.9)
        return score;
    }

    public static List<String> getMappedResponseList(String response) throws MapperException {
        ObjectMapper mapper = new ObjectMapper();
        List list;

        try {
            list = mapper.readValue(response, List.class); //it's a raw json so it's easier to put the data in a list
        } catch (Exception e) {
            LOG.debug("mapper exception: {}", e.getMessage());
            throw new MapperException("Mapper could not map the returned json into a list from amazon API");
        }
        //remove the [ and ] from the string
        String words = list.get(1).toString().replaceAll("[\\[\\]]", "");
        LOG.debug("response words from amazon: {}", words);

        String[] wordsList = words.trim().split("\\s*,\\s*");

        return Arrays.asList(wordsList);
    }


}
