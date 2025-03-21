package com.airline.management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TicketDTO {
    @NotBlank(message = "Passenger name is required")
    private String passengerName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String passengerEmail;

    @NotBlank(message = "Phone number is required")
    private String passengerPhone;

    @NotNull(message = "Schedule ID is required")
    private Long scheduleId;

    // Getters and Setters
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public String getPassengerEmail() { return passengerEmail; }
    public void setPassengerEmail(String passengerEmail) { this.passengerEmail = passengerEmail; }

    public String getPassengerPhone() { return passengerPhone; }
    public void setPassengerPhone(String passengerPhone) { this.passengerPhone = passengerPhone; }

    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }
}