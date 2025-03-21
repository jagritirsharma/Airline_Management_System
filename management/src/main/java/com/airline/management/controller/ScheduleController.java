package com.airline.management.controller;

import com.airline.management.model.Flight;
import com.airline.management.model.Schedule;
import com.airline.management.service.FlightService;
import com.airline.management.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/flights/{flightId}/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private FlightService flightService;

    @GetMapping
    public String getSchedulesByFlightId(
            @PathVariable Long flightId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) List<LocalDate> dates,
            Model model) {
        Flight flight = flightService.getFlightById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + flightId));

        List<Schedule> schedules = scheduleService.getSchedulesByFlightIdAndDates(flightId, dates);

        model.addAttribute("flight", flight);
        model.addAttribute("schedules", schedules);
        model.addAttribute("dates", dates);

        return "schedules";
    }

    // REST API endpoints for AJAX calls
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Schedule>> getSchedulesByFlightIdApi(
            @PathVariable Long flightId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) List<LocalDate> dates) {
        List<Schedule> schedules = scheduleService.getSchedulesByFlightIdAndDates(flightId, dates);
        return ResponseEntity.ok(schedules);
    }
}
