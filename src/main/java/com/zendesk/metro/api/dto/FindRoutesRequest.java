package com.zendesk.metro.api.dto;

public class FindRoutesRequest {
    private String sourceStationName;
    private String destinationStationName;
    private String timeOfTravel;
    private boolean json;

    public String getSourceStationName() {
        return sourceStationName;
    }

    public void setSourceStationName(String sourceStationName) {
        this.sourceStationName = sourceStationName;
    }

    public String getDestinationStationName() {
        return destinationStationName;
    }

    public void setDestinationStationName(String destinationStationName) {
        this.destinationStationName = destinationStationName;
    }

    public String getTimeOfTravel() {
        return timeOfTravel;
    }

    public void setTimeOfTravel(String timeOfTravel) {
        this.timeOfTravel = timeOfTravel;
    }

    public boolean isJson() {
        return json;
    }

    public void setJson(boolean json) {
        this.json = json;
    }

    @Override
    public String toString() {
        return "FindRoutesRequest{"
                + "sourceStationName='"
                + sourceStationName
                + '\''
                + ", destinationStationName='"
                + destinationStationName
                + '\''
                + ", timeOfTravel='"
                + timeOfTravel
                + '\''
                + '}';
    }
}
