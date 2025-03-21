package com.airline.management.service;

import com.airline.management.dto.TicketDTO;
import com.airline.management.exception.ResourceNotFoundException;
import com.airline.management.exception.ValidationException;
import com.airline.management.model.Schedule;
import com.airline.management.model.Ticket;
import com.airline.management.repository.ScheduleRepository;
import com.airline.management.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    @Transactional
    public Ticket createTicket(TicketDTO ticketDTO) {
        Schedule schedule = scheduleRepository.findById(ticketDTO.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + ticketDTO.getScheduleId()));

        if (schedule.getAvailableSeats() <= 0) {
            throw new ValidationException("No available seats for this flight schedule");
        }

        Ticket ticket = new Ticket();
        ticket.setPassengerName(ticketDTO.getPassengerName());
        ticket.setPassengerEmail(ticketDTO.getPassengerEmail());
        ticket.setPassengerPhone(ticketDTO.getPassengerPhone());
        ticket.setSchedule(schedule);
        ticket.setTotalPrice(schedule.getPrice());
        ticket.setBookingTime(LocalDateTime.now());

        // Generate a random seat number (A simple approach for demonstration)
        Random random = new Random();
        int rowNumber = random.nextInt(30) + 1;
        char seatLetter = (char)('A' + random.nextInt(6));
        ticket.setSeatNumber(rowNumber + String.valueOf(seatLetter));

        // Update available seats
        schedule.setAvailableSeats(schedule.getAvailableSeats() - 1);
        scheduleRepository.save(schedule);

        return ticketRepository.save(ticket);
    }

    @Transactional
    public void cancelTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));

        if (ticket.getStatus() == Ticket.TicketStatus.CANCELLED) {
            throw new ValidationException("Ticket is already cancelled");
        }

        // Update ticket status
        ticket.setStatus(Ticket.TicketStatus.CANCELLED);
        ticketRepository.save(ticket);

        // Update available seats in schedule
        Schedule schedule = ticket.getSchedule();
        schedule.setAvailableSeats(schedule.getAvailableSeats() + 1);
        scheduleRepository.save(schedule);
    }
}
