package com.sellics.unit;

import com.sellics.exception.MapperException;
import com.sellics.util.SearchVolumeUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SearchVolumeUtilTest {

    @Test
    public void keywordIsFoundInResponseTest() {
        List<String> wordsList = new ArrayList<>();
        wordsList.add("iphone charger");
        wordsList.add("ipad charger");

        Assert.assertEquals(true, SearchVolumeUtil.keywordIsFoundInResponse(wordsList, "ipad charger"));
        Assert.assertEquals(false, SearchVolumeUtil.keywordIsFoundInResponse(wordsList, "ipad"));
    }

    @Test
    public void estimatedScoreTest1() {
        int keywordLength = 14;
        int keywordFoundAtChar = 1;
        int amazonResponseSize = 10;

        Assert.assertEquals(100, SearchVolumeUtil.getEstimatedScore(keywordLength, keywordFoundAtChar, amazonResponseSize));
    }

    @Test
    public void estimatedScoreTest2() {
        int keywordLength = 12;
        int keywordFoundAtChar = 3;
        int amazonResponseSize = 10;

        Assert.assertEquals(75, SearchVolumeUtil.getEstimatedScore(keywordLength, keywordFoundAtChar, amazonResponseSize));
    }

    @Test
    public void estimatedScoreTest3() {
        int keywordLength = 12;
        int keywordFoundAtChar = 12;
        int amazonResponseSize = 10;

        Assert.assertEquals(0, SearchVolumeUtil.getEstimatedScore(keywordLength, keywordFoundAtChar, amazonResponseSize));
    }

    @Test
    public void mappedResponseListTest1() throws MapperException {
        String response = "[\"iphone charger\",[\"iphone charger\",\"iphone charger cable\",\"iphone charger 10 ft\",\"iphone charger 6 ft\",\"iphone charger apple certified\",\"iphone charger with wall plug\",\"iphone charger cord\",\"iphone charger and headphones adapter\",\"iphone charger 3ft\",\"apple iphone charger\"],[{\"nodes\":[{\"name\":\"Cell Phones & Accessories\",\"alias\":\"mobile\"}]},{},{},{},{},{},{},{},{},{}],[],\"2062UWJX9GQI5\"]";
        List<String> mappedResponse = SearchVolumeUtil.getMappedResponseList(response);
        Assert.assertEquals("iphone charger", mappedResponse.get(0));
        Assert.assertEquals("apple iphone charger", mappedResponse.get(9));
    }

    @Test(expected = MapperException.class)
    public void mappedResponseListExceptionTest() throws MapperException {
        String response = "{\"helloo\": \"yellow\"}";
        List<String> mappedResponse = SearchVolumeUtil.getMappedResponseList(response);
    }
}
