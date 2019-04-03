package com.zendesk.metro.processing.route;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Path {
    private List<Hop> lineStations;
    private int totalTime;

    public Path() {
        lineStations = new LinkedList<>();
        totalTime = 0;
    }

    public Path(Path other) {
        this();
        this.lineStations.addAll(other.lineStations);
        this.totalTime = other.totalTime;
    }

    public List<Hop> getLineStations() {
        return lineStations;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public List<String> route() {
        return lineStations.stream()
                .map(hop -> hop.getLineStation().getId())
                .collect(Collectors.toList());
    }

    public int last() {
        return lineStations.size() - 1;
    }

    @Override
    public String toString() {
        return String.format("LineStations: %s\nTotalTime: %d\n", lineStations, totalTime);
    }
}
