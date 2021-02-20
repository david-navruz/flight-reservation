package com.udemy.controllers;

import com.udemy.repositories.FlightRepository;
import com.udemy.entities.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class FlightController {

    @Autowired
    FlightRepository flightRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightController.class);


    @RequestMapping(value = "/findFlights", method = {RequestMethod.GET, RequestMethod.POST})
    public String findFlights(@RequestParam("from") String from, @RequestParam("to") String to,
                              @RequestParam("departureDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date departureDate,
                              ModelMap modelMap) {
        LOGGER.info("Inside findFlights() From:" + from + " TO:" + to + "Departure Date: " + departureDate);
        List<Flight> flights = flightRepository.findFlights(from, to, departureDate);
        modelMap.addAttribute("flights", flights);
        LOGGER.info("Flight Found are:" + flights);
        return "displayFlights";
    }

    @RequestMapping("admin/showAddFlight")
    public String showAddFlight() {
        return "addFlight";
    }

}
