package com.zendesk.metro.processing.route;

import com.zendesk.metro.processing.metrosystem.LineStation;

import java.util.Calendar;

public class Hop {
    private LineStation lineStation;
    private LineStation fromStation;
    private Calendar boardingTime;
    private boolean changeLine;

    public Hop(LineStation lineStation, LineStation fromStation, Calendar boardingTime) {
        this.lineStation = lineStation;
        this.fromStation = fromStation;
        this.boardingTime = boardingTime;
        if (fromStation == null) {
            changeLine = false;
        } else {
            changeLine = !lineStation.getStationCode().equals(fromStation.getStationCode());
        }
    }

    public LineStation getLineStation() {
        return lineStation;
    }

    public LineStation getFromStation() {
        return fromStation;
    }

    public boolean isChangeLine() {
        return changeLine;
    }
}
