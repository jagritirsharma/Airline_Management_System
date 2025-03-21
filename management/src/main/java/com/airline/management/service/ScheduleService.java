package com.airline.management.service;

import com.airline.management.model.Schedule;
import com.airline.management.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> getSchedulesByFlightIdAndDates(Long flightId, List<LocalDate> dates) {
        if (dates == null || dates.isEmpty()) {
            LocalDate today = LocalDate.now();
            dates = List.of(today);
        }

        LocalDateTime start = dates.get(0).atStartOfDay();
        LocalDateTime end = dates.get(dates.size() - 1).atTime(LocalTime.MAX);

        return scheduleRepository.findByFlightIdAndDepartureTimeBetween(flightId, start, end);
    }

    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }
}
