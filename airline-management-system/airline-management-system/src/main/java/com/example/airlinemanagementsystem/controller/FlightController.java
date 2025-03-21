package com.example.airlinemanagementsystem.controller;

import com.example.airlinemanagementsystem.model.Flight;
import com.example.airlinemanagementsystem.model.Schedule;
import com.example.airlinemanagementsystem.service.FlightService;
import com.example.airlinemanagementsystem.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public List<Flight> getAllFlights(@RequestParam(defaultValue = "asc") String sort) {
        return flightService.getAllFlights(sort);
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    @GetMapping("/{id}/schedules")
    public List<Schedule> getFlightSchedules(@PathVariable Long id) {
        return scheduleService.getSchedulesByFlightId(id);
    }
}