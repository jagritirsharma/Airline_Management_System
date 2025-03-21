package com.example.airlinemanagementsystem.service;

import com.example.airlinemanagementsystem.model.Schedule;
import com.example.airlinemanagementsystem.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> getSchedulesByFlightId(Long flightId) {
        return scheduleRepository.findByFlightId(flightId);
    }
}