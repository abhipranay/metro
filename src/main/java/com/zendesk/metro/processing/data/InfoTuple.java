package com.zendesk.metro.processing.data;

import java.util.Calendar;

public class InfoTuple {
    private String stationCode;
    private int stationNumber;
    private String stationName;
    private Calendar openingDate;

    InfoTuple(String stationCode, int stationNumber, String stationName, Calendar openingDate) {
        this.stationCode = stationCode;
        this.stationNumber = stationNumber;
        this.stationName = stationName;
        this.openingDate = openingDate;
    }

    public String getStationCode() {
        return stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public Calendar getOpeningDate() {
        return openingDate;
    }

    public int getStationNumber() {
        return stationNumber;
    }
}
