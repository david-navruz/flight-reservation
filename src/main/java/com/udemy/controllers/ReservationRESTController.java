package com.udemy.controllers;

import com.udemy.entities.Reservation;
import com.udemy.flightreservation.dto.ReservationUpdateRequest;
import com.udemy.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ReservationRESTController {

    @Autowired
    ReservationRepository reservationRepository;


    @RequestMapping(value = "/reservations/{id}")
    public Reservation findReservation(@PathVariable("id") Long id) {
       Reservation reservation = reservationRepository.findById(id).get();
       return reservation;
    }

    @RequestMapping(value = "/reservations")
    public Reservation updateReservation(@RequestBody ReservationUpdateRequest reservationUpdateRequest){
        Reservation updatedReservation = reservationRepository.findById(reservationUpdateRequest.getId()).get();
        updatedReservation.setCheckedIn(reservationUpdateRequest.isCheckedIn());
        updatedReservation.setNumberOfBags(reservationUpdateRequest.getNumberOfBags());
        Reservation savedReservation = reservationRepository.save(updatedReservation);
        return savedReservation;
    }



}
