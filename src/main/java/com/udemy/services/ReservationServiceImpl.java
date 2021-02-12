package com.udemy.services;

import com.udemy.entities.Flight;
import com.udemy.entities.Passenger;
import com.udemy.entities.Reservation;
import com.udemy.flightreservation.dto.ReservationRequest;
import com.udemy.repositories.FlightRepository;
import com.udemy.repositories.PassengerRepository;
import com.udemy.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    ReservationRepository reservationRepository;


    @Override
    public Reservation bookFlight(ReservationRequest reservationRequest) {
        // get the flight info
        Long flightId = reservationRequest.getFlightId();
        Flight flight = flightRepository.findById(flightId).get();

        // creating the passenger object
        Passenger passenger = new Passenger();
        passenger.setFirstName(reservationRequest.getPassengerFirstName());
        passenger.setLastName(reservationRequest.getPassengerLastName());
        passenger.setPhone(reservationRequest.getPassengerPhone());
        passenger.setEmail(reservationRequest.getPassengerPhone());
        // persisting the passenger object
        Passenger savedPassenger = passengerRepository.save(passenger);

        // creating the reservation object
        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);
        reservation.setCheckedIn(false);
        reservation.setNumberOfBags(0);
        // persisting the reservation object
        Reservation savedReservation = reservationRepository.save(reservation);
        return savedReservation;
    }


}
