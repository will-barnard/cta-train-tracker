package com.barnard.javactatraintracker.controller;

import com.barnard.javactatraintracker.model.ArrivalsParams;
import com.barnard.javactatraintracker.model.FollowThisTrainParams;
import com.barnard.javactatraintracker.model.LocationsParams;
import com.barnard.javactatraintracker.service.TrainTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@CrossOrigin
public class TrainTrackerController {

    @Autowired
    private TrainTrackerService trainTrackerService;

    @GetMapping(path="/test")
    public Object getTest() {
        return trainTrackerService.getTrainStatus();
    }

    @PostMapping(path="/arrivals")
    public Object getArrivals(@RequestBody ArrivalsParams arrivalsParams) {
        return trainTrackerService.getArrivals(arrivalsParams);
    }

    @PostMapping(path="/follow")
    public Object getFollowThisTrain(@RequestBody FollowThisTrainParams followThisTrainParams) {
        return trainTrackerService.getFollowThisTrain(followThisTrainParams);
    }

    @PostMapping(path="/locations")
    public Object getLocations(@RequestBody LocationsParams locationsParams) {
        return trainTrackerService.getLocations(locationsParams);
    }

}