package com.example.airlinemanagementsystem.repository;

import com.example.airlinemanagementsystem.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}