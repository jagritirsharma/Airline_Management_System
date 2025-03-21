package com.airline.management.controller;

import com.airline.management.model.Flight;
import com.airline.management.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping
    public String getAllFlights(
            @RequestParam(value = "sort", defaultValue = "asc") String sortDirection,
            Model model) {
        List<Flight> flights = flightService.getAllFlights(sortDirection);
        model.addAttribute("flights", flights);
        model.addAttribute("sortDirection", sortDirection);
        return "flights";
    }

    @GetMapping("/{id}")
    public String getFlightById(@PathVariable Long id, Model model) {
        Flight flight = flightService.getFlightById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
        model.addAttribute("flight", flight);
        return "flight-details";
    }

    // REST API endpoints for AJAX calls
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Flight>> getFlightsApi(
            @RequestParam(value = "sort", defaultValue = "asc") String sortDirection) {
        List<Flight> flights = flightService.getAllFlights(sortDirection);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Flight> getFlightByIdApi(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
        return ResponseEntity.ok(flight);
    }
}
