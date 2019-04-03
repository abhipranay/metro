package com.zendesk.metro.processing.route;

import com.zendesk.metro.processing.metrosystem.LineStation;
import com.zendesk.metro.processing.metrosystem.MetroStation;
import com.zendesk.metro.processing.metrosystem.MetroSystem;
import com.zendesk.metro.processing.metrosystem.TravelTimeStrategy;

import java.util.*;

public class RoutesFinder {
    private MetroSystem metroSystem;
    private TravelTimeStrategy timeCalculator;
    private static final int maxPaths = 5;

    public RoutesFinder(MetroSystem metroSystem, TravelTimeStrategy timeCalculator) {
        this.metroSystem = metroSystem;
        this.timeCalculator = timeCalculator;
    }

    public List<Path> findAllPaths(
            MetroStation source, MetroStation destination, Calendar timeOfTravel) {
        PriorityQueue<Path> routes =
                new PriorityQueue<>((o1, o2) -> o2.getTotalTime() - o1.getTotalTime());
        LinkedList<Path> paths = new LinkedList<>();
        Map<MetroStation, List<Path>> memo = new HashMap<>();
        List<LineStation> lineStationsAtStation = metroSystem.getLineStations(source);
        int maxLineChange = metroSystem.totalLines();
        for (LineStation lineStation : lineStationsAtStation) {
            Set<String> visited = new HashSet<>();
            Path currentPath = new Path();
            findPaths(
                    lineStation,
                    destination,
                    null,
                    timeOfTravel,
                    visited,
                    routes,
                    currentPath,
                    maxLineChange,
                    0);
        }

        while (routes.size() > 0) {
            paths.addFirst(routes.poll());
        }
        return new ArrayList<>(paths);
    }

    private void findPaths(
            LineStation sourceLineStation,
            MetroStation destination,
            LineStation from,
            Calendar timeOfTravel,
            Set<String> visited,
            PriorityQueue<Path> routes,
            Path currentPath,
            int lineChangesLeft,
            int totalTime) {
        visited.add(sourceLineStation.getId());
        currentPath.getLineStations().add(new Hop(sourceLineStation, from, timeOfTravel));
        if (sourceLineStation.getMetroStation().equals(destination)) {
            Path newPath = new Path(currentPath);
            newPath.setTotalTime(totalTime);
            if (routes.size() >= maxPaths) {
                routes.poll();
            }
            routes.add(newPath);
        } else {
            List<LineStation> nextLineStations = metroSystem.getNextLineStations(sourceLineStation);
            for (LineStation lineStation : nextLineStations) {
                if (!visited.contains(lineStation.getId()) && lineChangesLeft > 0) {
                    if (!sourceLineStation.getStationCode().equals(lineStation.getStationCode())) {
                        lineChangesLeft--;
                    }

                    int travelMinutes =
                            timeCalculator.getTimePerHop(
                                    sourceLineStation, lineStation, timeOfTravel);
                    Calendar nextStationArrivalTime = (Calendar) timeOfTravel.clone();
                    nextStationArrivalTime.add(Calendar.MINUTE, travelMinutes);
                    if (timeCalculator.isOperational(lineStation, nextStationArrivalTime)) {
                        if (routes.size() == 0 || totalTime < routes.peek().getTotalTime()) {
                            findPaths(
                                    lineStation,
                                    destination,
                                    sourceLineStation,
                                    nextStationArrivalTime,
                                    visited,
                                    routes,
                                    currentPath,
                                    lineChangesLeft,
                                    totalTime + travelMinutes);
                        }
                    }
                }
            }
        }
        visited.remove(sourceLineStation.getId());
        currentPath.getLineStations().remove(currentPath.last());
    }
}
