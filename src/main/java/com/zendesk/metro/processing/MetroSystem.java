package com.zendesk.metro.processing;

import java.util.*;
import java.util.stream.Collectors;

public class MetroSystem {
    private Map<String, List<LineStation>> lineStationsOnLine;
    private Map<MetroStation, List<LineStation>> stationLineMap;

    private MetroSystem() {
        lineStationsOnLine = new HashMap<>();
        stationLineMap = new HashMap<>();
    }

    public static MetroSystem getInstance() {
        return new MetroSystem();
    }

    public MetroStation getMetroStationByName(String name) {
        return stationLineMap.get(new MetroStation(name)).get(0).getMetroStation();
    }

    public void boot(Iterable<InfoTuple> infoTuples) {
        infoTuples.forEach(
                infoTuple -> {
                    String code = infoTuple.getStationCode();
                    int number = infoTuple.getStationNumber();
                    String stationName = infoTuple.getStationName();
                    Calendar openingDate = infoTuple.getOpeningDate();

                    MetroStation metroStation = new MetroStation(stationName);
                    LineStation lineStation =
                            new LineStation(code, number, metroStation, openingDate);

                    if (!lineStationsOnLine.containsKey(code)) {
                        lineStationsOnLine.put(code, new ArrayList<>());
                    }
                    lineStationsOnLine.computeIfAbsent(code, x -> new ArrayList<>());
                    lineStationsOnLine.get(code).add(lineStation);

                    // put line station in station line map
                    stationLineMap.computeIfAbsent(metroStation, key -> new ArrayList<>());
                    stationLineMap.get(metroStation).add(lineStation);
                });

        // Stations on line in sorted order of numbers
        lineStationsOnLine.forEach(
                (s, lineStations) -> {
                    lineStations.sort(Comparator.comparingInt(LineStation::getStationNumber));
                });
    }

    List<LineStation> getLineStations(MetroStation metroStation) {
        return stationLineMap.get(metroStation);
    }

    /**
     * Function returns adjacent lineStations to a given lineStation.
     *
     * @param lineStation
     * @return
     */
    List<LineStation> getNextLineStations(LineStation lineStation) {
        List<LineStation> next = new ArrayList<>();

        Comparator<LineStation> c = Comparator.comparingInt(LineStation::getStationNumber);

        List<LineStation> lineStations = lineStationsOnLine.get(lineStation.getStationCode());
        int pos =
                Collections.binarySearch(
                        lineStations,
                        new LineStation(
                                lineStation.getStationCode(),
                                lineStation.getStationNumber(),
                                null,
                                null),
                        c);
        if (pos + 1 < lineStations.size()) {
            next.add(lineStations.get(pos + 1));
        }
        if (pos - 1 >= 0) {
            next.add(lineStations.get(pos - 1));
        }

        // on different line
        List<LineStation> temp =
                stationLineMap.get(lineStation.getMetroStation()).stream()
                        .filter(
                                lineStation1 ->
                                        !next.contains(lineStation1)
                                                && !lineStation1.equals(lineStation))
                        .collect(Collectors.toList());
        next.addAll(temp);
        return next;
    }

    int totalLines() {
        return lineStationsOnLine.keySet().size();
    }
}
