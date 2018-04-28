package com.sellics.controller;

import com.sellics.AmazonsearchvolumeApplication;
import com.sellics.service.ScoreService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmazonsearchvolumeApplication.class)
@ComponentScan("com.sellics")
@EnableAutoConfiguration
@WebAppConfiguration
public class ScoreControllerTest {

    private static final String URL = "/v1/score";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Value("${amazonURI}")
    private String amazonURI;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();

    }

    @Test
    public void getScoreTest1() throws Exception {
        String keyword = "/linux";
        mockMvc.perform(get(URL + keyword)
                .contentType(contentType))
                .andExpect(jsonPath("$.keyword", is("linux")))
                .andExpect(jsonPath("$.score", is(100)));
    }

    @Test
    public void getScoreTest2() throws Exception {
        String keyword = "/knys";
        mockMvc.perform(get(URL + keyword)
                .contentType(contentType))
                .andExpect(jsonPath("$.keyword", is("knys")))
                .andExpect(jsonPath("$.score", is(30)));
    }

    @Test
    public void getScoreTest3() throws Exception {
        String keyword = "/gohii";
        mockMvc.perform(get(URL + keyword)
                .contentType(contentType))
                .andExpect(jsonPath("$.keyword", is("gohii")))
                .andExpect(jsonPath("$.score", is(0)));
    }

    @Test
    public void checkAmazonApiIsWorkingTest() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(amazonURI + "hello", String.class);
        assertNotNull(response);
    }
}
