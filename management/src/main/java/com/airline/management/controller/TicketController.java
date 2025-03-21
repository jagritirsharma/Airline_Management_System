package com.airline.management.controller;

import com.airline.management.dto.TicketDTO;
import com.airline.management.model.Schedule;
import com.airline.management.model.Ticket;
import com.airline.management.service.ScheduleService;
import com.airline.management.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/create")
    public String showTicketForm(@RequestParam(required = true) Long scheduleId, Model model) {
        Schedule schedule = scheduleService.getScheduleById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + scheduleId));

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setScheduleId(scheduleId);

        model.addAttribute("ticketDTO", ticketDTO);
        model.addAttribute("schedule", schedule);
        model.addAttribute("flight", schedule.getFlight());

        return "create-ticket";
    }

    @PostMapping
    public String createTicket(@Valid TicketDTO ticketDTO, BindingResult result,
                               RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            Schedule schedule = scheduleService.getScheduleById(ticketDTO.getScheduleId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + ticketDTO.getScheduleId()));

            model.addAttribute("schedule", schedule);
            model.addAttribute("flight", schedule.getFlight());
            return "create-ticket";
        }

        Ticket ticket = ticketService.createTicket(ticketDTO);
        redirectAttributes.addFlashAttribute("message", "Ticket booked successfully!");
        return "redirect:/tickets/" + ticket.getId();
    }

    @GetMapping("/{id}")
    public String getTicketById(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.getTicketById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));

        model.addAttribute("ticket", ticket);
        model.addAttribute("schedule", ticket.getSchedule());
        model.addAttribute("flight", ticket.getSchedule().getFlight());

        return "ticket-details";
    }

    @GetMapping("/{id}/cancel")
    public String cancelTicketForm(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.getTicketById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));

        model.addAttribute("ticket", ticket);
        model.addAttribute("schedule", ticket.getSchedule());
        model.addAttribute("flight", ticket.getSchedule().getFlight());

        return "cancel-ticket";
    }

    @PostMapping("/{id}/cancel")
    public String cancelTicket(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ticketService.cancelTicket(id);
        redirectAttributes.addFlashAttribute("message", "Ticket cancelled successfully!");
        return "redirect:/tickets/" + id;
    }

    // REST API endpoints for AJAX calls
    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Ticket> getTicketByIdApi(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<Ticket> createTicketApi(@Valid @RequestBody TicketDTO ticketDTO) {
        Ticket ticket = ticketService.createTicket(ticketDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteTicketApi(@PathVariable Long id) {
        ticketService.cancelTicket(id);
        return ResponseEntity.noContent().build();
    }
}
