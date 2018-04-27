package com.sellics.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScoreDTO {

    private String keyword;
    private int score;

    public ScoreDTO() {
    }

    public ScoreDTO(String keyword, int score) {
        this.keyword = keyword;
        this.score = score;
    }

    public static ScoreDTOBuilder newBuilder(){
        return new ScoreDTOBuilder();
    }

    public String getKeyword() {
        return keyword;
    }

    public int getScore() {
        return score;
    }

    public static class ScoreDTOBuilder{
        private String keyword;
        private int score;

        public ScoreDTOBuilder setKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public ScoreDTOBuilder setScore(int score) {
            this.score = score;
            return this;
        }

        public ScoreDTO createScoreDTO(){
            return new ScoreDTO(keyword, score);
        }
    }
}
