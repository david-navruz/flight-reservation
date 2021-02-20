package com.udemy.controllers;

import com.udemy.entities.Reservation;
import com.udemy.flightreservation.dto.ReservationUpdateRequest;
import com.udemy.repositories.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ReservationRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRESTController.class);

    @Autowired
    ReservationRepository reservationRepository;


    @RequestMapping(value = "/reservations/{id}")
    public Reservation findReservation(@PathVariable("id") Long id) {
        LOGGER.info("Inside findReservation() for id: " + id);
        Reservation reservation = reservationRepository.findById(id).get();
        return reservation;
    }

    @RequestMapping(value = "/reservations")
    public Reservation updateReservation(@RequestBody ReservationUpdateRequest reservationUpdateRequest) {
        LOGGER.info("Inside updateReservation() for " + reservationUpdateRequest);
        Reservation updatedReservation = reservationRepository.findById(reservationUpdateRequest.getId()).get();
        updatedReservation.setCheckedIn(reservationUpdateRequest.isCheckedIn());
        updatedReservation.setNumberOfBags(reservationUpdateRequest.getNumberOfBags());
        LOGGER.info("Saving Reservation");
        Reservation savedReservation = reservationRepository.save(updatedReservation);
        return savedReservation;
    }



}
