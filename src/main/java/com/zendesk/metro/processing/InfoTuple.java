package com.zendesk.metro.processing;

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

    String getStationCode() {
        return stationCode;
    }

    String getStationName() {
        return stationName;
    }

    Calendar getOpeningDate() {
        return openingDate;
    }

    int getStationNumber() {
        return stationNumber;
    }
}
