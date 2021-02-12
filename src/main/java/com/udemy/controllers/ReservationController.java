package com.udemy.controllers;

import com.udemy.entities.Flight;
import com.udemy.entities.Reservation;
import com.udemy.flightreservation.dto.ReservationRequest;
import com.udemy.repositories.FlightRepository;
import com.udemy.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationController {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    ReservationService reservationService;


    @RequestMapping(value = "/showCompleteReservation", method = {RequestMethod.GET, RequestMethod.POST})
    public String showCompleteReservation(@RequestParam("flightId") Long flightId, ModelMap modelMap){
        Flight flight = flightRepository.findById(flightId).get();
        modelMap.addAttribute("flight", flight);
        return "completeReservation";
    }


    @RequestMapping(value = "/completeReservation", method = {RequestMethod.GET, RequestMethod.POST})
    public String completeReservation(ReservationRequest reservationRequest, ModelMap modelMap) {
        // creating the reservation object with the reservation request
        Reservation reservation = reservationService.bookFlight(reservationRequest);
        modelMap.addAttribute("msg", "Reservation created successfully. Id is " + reservation.getId());
        return "reservationConfirmation";
    }




}
