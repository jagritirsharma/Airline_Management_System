package com.example.airlinemanagementsystem.repository;

import com.example.airlinemanagementsystem.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByFlightId(Long flightId);
}