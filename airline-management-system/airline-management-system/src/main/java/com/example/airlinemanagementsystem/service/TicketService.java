package com.example.airlinemanagementsystem.service;


import com.example.airlinemanagementsystem.model.Schedule;
import com.example.airlinemanagementsystem.model.Ticket;
import com.example.airlinemanagementsystem.repository.ScheduleRepository;
import com.example.airlinemanagementsystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Transactional
    public Ticket createTicket(Ticket ticket, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (schedule.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }

        schedule.setAvailableSeats(schedule.getAvailableSeats() - 1);
        ticket.setSchedule(schedule);
        ticket.setStatus("BOOKED");
        return ticketRepository.save(ticket);
    }

    public Ticket getTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    @Transactional
    public void deleteTicket(Long id) {
        Ticket ticket = getTicket(id);
        Schedule schedule = ticket.getSchedule();
        schedule.setAvailableSeats(schedule.getAvailableSeats() + 1);
        ticketRepository.delete(ticket);
    }
}