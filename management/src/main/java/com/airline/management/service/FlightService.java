package com.airline.management.service;

import com.airline.management.model.Flight;
import com.airline.management.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> getAllFlights(String sortDirection) {
        if ("desc".equalsIgnoreCase(sortDirection)) {
            return flightRepository.findAllByOrderByAirlineDesc();
        }
        return flightRepository.findAllByOrderByAirlineAsc();
    }

    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }
}
