package com.zendesk.metro;

import com.zendesk.metro.processing.contracts.IStationDataReader;
import com.zendesk.metro.processing.data.CsvStationDataReader;
import com.zendesk.metro.processing.data.InfoTuple;
import com.zendesk.metro.processing.metrosystem.MetroSystem;
import com.zendesk.metro.processing.metrosystem.TravelTimeStrategy;
import com.zendesk.metro.processing.route.RoutesFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
public class MetroApplication {

    @Bean
    public IStationDataReader dataReader() {
        return new CsvStationDataReader();
    }

    @Bean
    @Autowired
    public MetroSystem metroSystem(IStationDataReader dataReader)
            throws IOException, ParseException {
        Iterable<InfoTuple> infoTuples = dataReader.readData();
        MetroSystem metroSystem = MetroSystem.getInstance();
        metroSystem.boot(infoTuples);
        return metroSystem;
    }

    @Bean
    public TravelTimeStrategy travelTimeCalculator() {
        return new TravelTimeStrategy();
    }

    @Bean
    @Autowired
    public RoutesFinder metroGraph(MetroSystem metroSystem, TravelTimeStrategy travelTimeStrategy) {
        return new RoutesFinder(metroSystem, travelTimeStrategy);
    }

    public static void main(String[] args) {
        SpringApplication.run(MetroApplication.class, args);
    }
}
