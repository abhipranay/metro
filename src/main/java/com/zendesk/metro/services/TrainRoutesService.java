package com.zendesk.metro.services;

import com.zendesk.metro.processing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class TrainRoutesService {
    @Autowired private MetroSystem metroSystem;

    @Autowired private RoutesFinder graph;

    @Autowired private TravelStepsMaker stepsMaker;

    public MetroStation getMetroStation(String name) {
        return metroSystem.getMetroStationByName(name);
    }

    public List<Path> getRoutes(
            MetroStation sourceStation, MetroStation destStation, Calendar timeOfTravel) {
        return graph.findAllPaths(sourceStation, destStation, timeOfTravel);
    }

    public List<List<String>> getSteps(List<Path> paths) {
        List<List<String>> humanReadableSteps = new ArrayList<>();
        for (Path path : paths) {
            List<String> stepsWithTime = stepsMaker.getSteps(path);
            stepsWithTime.add(
                    String.format(
                            "You will take %d minutes to reach destination", path.getTotalTime()));
            stepsWithTime.add(String.format("Route: %s", path.route()));
            humanReadableSteps.add(stepsWithTime);
        }
        return humanReadableSteps;
    }
}
