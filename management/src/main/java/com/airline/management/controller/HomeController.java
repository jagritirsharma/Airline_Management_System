package com.airline.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling main navigation and home page requests
 */
@Controller
public class HomeController {

    /**
     * Handles the root path request and redirects to the flights page
     *
     * @return String redirect URL
     */
    @GetMapping("/")
    public String redirectToFlights() {
        return "redirect:/flights";
    }

    /**
     * Serves the home page
     *
     * @return String view name for the home page
     */
    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}