package com.zendesk.metro.api;

import com.zendesk.metro.api.dto.FindRoutesRequest;
import com.zendesk.metro.api.dto.TrainRoutesResponse;
import com.zendesk.metro.processing.metrosystem.MetroStation;
import com.zendesk.metro.processing.route.Path;
import com.zendesk.metro.services.TrainRoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/train-path")
public class TrainRoutesController {
    @Autowired private TrainRoutesService routesService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TrainRoutesResponse> findAllPaths(
            @RequestBody FindRoutesRequest routesRequest) {
        MetroStation sourceStation =
                routesService.getMetroStation(routesRequest.getSourceStationName().toLowerCase());

        MetroStation destStation =
                routesService.getMetroStation(
                        routesRequest.getDestinationStationName().toLowerCase());

        String pattern = "yyyy-MM-dd'T'HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date;
        try {
            String timeString = routesRequest.getTimeOfTravel().trim();
            date = simpleDateFormat.parse(timeString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            List<Path> routes = routesService.getRoutes(sourceStation, destStation, calendar);
            TrainRoutesResponse response = new TrainRoutesResponse();
            if (routesRequest.isJson()) {
                response.paths = routes;
            } else {
                response.steps = routesService.getSteps(routes);
            }
            return ResponseEntity.ok(response);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
