package com.udemy.services;

import com.udemy.entities.Reservation;
import com.udemy.flightreservation.dto.ReservationRequest;

public interface ReservationService {


    public Reservation bookFlight(ReservationRequest reservationRequest);



}
