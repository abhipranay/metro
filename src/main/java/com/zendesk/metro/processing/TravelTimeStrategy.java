package com.zendesk.metro.processing;

import java.util.Calendar;

/** Class contains constraints mentioned in Bonus section */
public class TravelTimeStrategy {
    int getTimePerHop(
            LineStation currentLineStation, LineStation nextLineStation, Calendar currentTime) {
        int dayOfWeek = currentTime.get(Calendar.DAY_OF_WEEK);
        int hourOfDay = currentTime.get(Calendar.HOUR_OF_DAY);
        int totalTime = 0;
        if (dayOfWeek >= 2
                && dayOfWeek <= 6
                && ((hourOfDay >= 6 && hourOfDay <= 9) || (hourOfDay >= 18 && hourOfDay <= 21))) {
            if (currentLineStation.getStationCode().equals("NS")
                    || currentLineStation.getStationCode().equals("NE")) {
                totalTime += 12;
            } else {
                totalTime += 10;
            }

            // station change
            if (!currentLineStation.getStationCode().equals(nextLineStation.getStationCode())) {
                totalTime += 15;
            }
        } else if ((hourOfDay < 6 || hourOfDay >= 22)) {
            if (currentLineStation.getStationCode().equals("TE")) {
                totalTime += 8;
            } else {
                totalTime += 10;
            }

            // station change
            if (!currentLineStation.getStationCode().equals(nextLineStation.getStationCode())) {
                totalTime += 10;
            }
        } else {
            if (currentLineStation.getStationCode().equals("DT")
                    || currentLineStation.getStationCode().equals("TE")) {
                totalTime += 8;
            } else {
                totalTime += 10;
            }

            // station change
            if (!currentLineStation.getStationCode().equals(nextLineStation.getStationCode())) {
                totalTime += 10;
            }
        }
        return totalTime;
    }

    boolean isOperational(LineStation lineStation, Calendar arrivalTime) {
        int hourOfDay = arrivalTime.get(Calendar.HOUR_OF_DAY);
        if (arrivalTime.before(lineStation.getOpeningDate())) {
            return false;
        }
        if ((hourOfDay < 6 || hourOfDay >= 22)) {
            return !lineStation.getStationCode().equals("DT")
                    && !lineStation.getStationCode().equals("CG")
                    && !lineStation.getStationCode().equals("CE");
        }
        return true;
    }
}
