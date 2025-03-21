package com.airline.management.repository;

import com.airline.management.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findAllByOrderByAirlineAsc();
    List<Flight> findAllByOrderByAirlineDesc();
}
