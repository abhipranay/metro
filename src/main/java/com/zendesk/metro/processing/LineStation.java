package com.zendesk.metro.processing;

import java.util.Calendar;
import java.util.Objects;

public class LineStation {
    private String stationCode;
    private int stationNumber;
    private Calendar openingDate;

    private MetroStation metroStation;

    public LineStation(
            String stationCode,
            int stationNumber,
            MetroStation metroStation,
            Calendar openingDate) {
        this.stationCode = stationCode;
        this.stationNumber = stationNumber;
        this.metroStation = metroStation;
        this.openingDate = openingDate;
    }

    String getStationCode() {
        return stationCode;
    }

    int getStationNumber() {
        return stationNumber;
    }

    Calendar getOpeningDate() {
        return openingDate;
    }

    public MetroStation getMetroStation() {
        return metroStation;
    }

    public String getId() {
        return String.format("%s%s", stationCode, stationNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineStation that = (LineStation) o;
        return stationNumber == that.stationNumber && Objects.equals(stationCode, that.stationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationCode, stationNumber);
    }
}
