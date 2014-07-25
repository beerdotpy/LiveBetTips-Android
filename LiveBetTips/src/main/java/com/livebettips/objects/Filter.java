package com.livebettips.objects;

import java.util.List;

public class Filter {

    private List<String> leagueType;
    private List<String> predictionName;
    private String units;

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

    public String getUnits(){
        return units;
    }
    public void setUnits(String unit){
        this.units = unit;
    }

}
