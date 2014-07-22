package com.livebettips.objects;

import java.util.List;

public class Filter {

    private List<String> leagueType;
    private List<String> predictionName;

    public List getLeague() {
        return leagueType;
    }

    public void setLeague(List<String> league) {
        this.leagueType = league;
    }

    public List getPredictionName() {
        return predictionName;
    }

    public void setPredictionName(List<String> predictionName) {
        this.predictionName = predictionName;
    }

}
