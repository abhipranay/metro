package com.zendesk.metro.processing;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TravelStepsMaker {
    public List<String> getSteps(Path path) {
        List<String> steps = new ArrayList<>();
        String step = "";
        for (int i = 0; i < path.getLineStations().size() - 1; i++) {
            Hop hop = path.getLineStations().get(i);
            if (hop.isChangeLine()) {
                steps.add(
                        String.format(
                                "Change from %s line to %s line",
                                hop.getFromStation().getStationCode(),
                                hop.getLineStation().getStationCode()));
            }
            Hop next = path.getLineStations().get(i + 1);
            steps.add(
                    String.format(
                            "Take %s line from %s to %s",
                            hop.getLineStation().getStationCode(),
                            hop.getLineStation().getMetroStation().getOriginalName(),
                            next.getLineStation().getMetroStation().getOriginalName()));
        }
        return steps;
    }
}
