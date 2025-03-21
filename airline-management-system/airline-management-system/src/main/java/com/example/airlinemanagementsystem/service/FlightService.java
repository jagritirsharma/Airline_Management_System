package com.example.airlinemanagementsystem.service;
import com.example.airlinemanagementsystem.model.Flight;
import com.example.airlinemanagementsystem.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> getAllFlights(String sort) {
        return flightRepository.findAll(Sort.by(sort.equals("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC, "id"));
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
    }
}