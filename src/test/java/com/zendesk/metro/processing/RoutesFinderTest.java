package com.zendesk.metro.processing;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoutesFinderTest {
    @Autowired private MetroSystem metroSystem;

    @Autowired private RoutesFinder routesFinder;

    @Before
    public void setUp() throws Exception {}

    @Test
    public void testRouteFromHollandVillageToBugisInPeakHours() {
        MetroStation source = new MetroStation("Holland Village");
        MetroStation destination = new MetroStation("Bugis");
        Calendar timeOfTravel = Calendar.getInstance();
        timeOfTravel.set(2019, Calendar.APRIL, 3, 6, 30);
        List<Path> paths = routesFinder.findAllPaths(source, destination, timeOfTravel);

        List<String> expectedRoute =
                Arrays.asList(
                        "CC21", "CC20", "CC19", "DT9", "DT10", "DT11", "DT12", "DT13", "DT14");
        Assert.assertEquals("Correct route is calculated", expectedRoute, paths.get(0).route());
        Assert.assertEquals(
                "Total time in peak hours should be 95 minutes", 95, paths.get(0).totalTime);
    }

    @Test
    public void testLineChangeTimeAddedForBoonLayToLittleIndiaInPeakHours() {
        MetroStation source = new MetroStation("Boon Lay");
        MetroStation destination = new MetroStation("Little India");
        Calendar timeOfTravel = Calendar.getInstance();
        timeOfTravel.set(2019, Calendar.APRIL, 3, 6, 30);
        List<Path> paths = routesFinder.findAllPaths(source, destination, timeOfTravel);
        List<String> expectedRoute =
                Arrays.asList(
                        "EW27", "EW26", "EW25", "EW24", "EW23", "EW22", "EW21", "CC22", "CC21",
                        "CC20", "CC19", "DT9", "DT10", "DT11", "DT12");
        Assert.assertEquals("Correct route is calculated", expectedRoute, paths.get(0).route());
        Assert.assertEquals(
                "Total time in peak hours should be 170 minutes", 170, paths.get(0).totalTime);
    }

    @Test
    public void testIfNoRouteToStationOpeningInFuture() {
        MetroStation source = new MetroStation("Little India");
        MetroStation destination = new MetroStation("Mount Pleasant");
        Calendar timeOfTravel = Calendar.getInstance();
        timeOfTravel.set(2019, Calendar.APRIL, 3, 6, 30);
        List<Path> paths = routesFinder.findAllPaths(source, destination, timeOfTravel);
        Assert.assertEquals("No routes are present if traveling in 2019", 0, paths.size());

        timeOfTravel.set(2022, Calendar.APRIL, 1);
        paths = routesFinder.findAllPaths(source, destination, timeOfTravel);
        Assert.assertNotEquals("Routes should be present if traveling in 2022", 0, paths.size());

        List<String> expectedRoute = Arrays.asList("DT12", "DT11", "DT10", "TE11", "TE10");
        Assert.assertEquals("Correct route is calculated", expectedRoute, paths.get(0).route());
        Assert.assertEquals(
                "Total time in peak hours should be 55 minutes", 55, paths.get(0).totalTime);
    }
}
