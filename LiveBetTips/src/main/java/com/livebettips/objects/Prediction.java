package com.livebettips.objects;

import java.util.HashMap;
import java.util.Map;

public class Prediction {

    private Integer id;
    private String leagueType;
    private String flagURL;
    private String homeTeam;
    private String awayTeam;
    private Boolean isCompleted;
    private Integer tipDetail;
    private String DateTimeCreated;
    private String isPredictionVerified;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Prediction withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getLeagueType() {
        return leagueType;
    }

    public void setLeagueType(String leagueType) {
        this.leagueType = leagueType;
    }

    public Prediction withLeagueType(String leagueType) {
        this.leagueType = leagueType;
        return this;
    }

    public String getFlagURL() {
        return flagURL;
    }

    public void setFlagURL(String flagURL) {
        this.flagURL = flagURL;
    }

    public Prediction withFlagURL(String flagURL) {
        this.flagURL = flagURL;
        return this;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Prediction withHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
        return this;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Prediction withAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
        return this;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Prediction withIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
        return this;
    }

    public Integer getTipDetail() {
        return tipDetail;
    }

    public void setTipDetail(Integer tipDetail) {
        this.tipDetail = tipDetail;
    }

    public Prediction withTipDetail(Integer tipDetail) {
        this.tipDetail = tipDetail;
        return this;
    }

    public String getDateTimeCreated() {
        return DateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.DateTimeCreated = dateTimeCreated;
    }

    public Prediction withDateTimeCreated(String dateTimeCreated) {
        this.DateTimeCreated = dateTimeCreated;
        return this;
    }

    public String getIsPredictionVerified() {
        return isPredictionVerified;
    }

    public void setIsPredictionVerified(String isPredictionVerified) {
        this.isPredictionVerified = isPredictionVerified;
    }

    public Prediction withIsPredictionVerified(String isPredictionVerified) {
        this.isPredictionVerified = isPredictionVerified;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
