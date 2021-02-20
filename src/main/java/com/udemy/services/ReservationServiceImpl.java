package com.udemy.services;

import com.udemy.entities.Flight;
import com.udemy.entities.Passenger;
import com.udemy.entities.Reservation;
import com.udemy.flightreservation.dto.ReservationRequest;
import com.udemy.flightreservation.util.EmailUtil;
import com.udemy.flightreservation.util.PdfGenerator;
import com.udemy.repositories.FlightRepository;
import com.udemy.repositories.PassengerRepository;
import com.udemy.repositories.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Value("${com.udemy.flightreservation.itinerary.dirpath}")
    private String ITINERARY_DIR;

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    PdfGenerator pdfGenerator;

    @Autowired
    EmailUtil emailUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Override
    @Transactional
    public Reservation bookFlight(ReservationRequest reservationRequest) {
        LOGGER.info("Inside bookFlight()");
        // get the flight info
        Long flightId = reservationRequest.getFlightId();
        LOGGER.info("Fetching  flight for flight id:" + flightId);
        Flight flight = flightRepository.findById(flightId).get();

        // creating the passenger object
        Passenger passenger = new Passenger();
        passenger.setFirstName(reservationRequest.getPassengerFirstName());
        passenger.setLastName(reservationRequest.getPassengerLastName());
        passenger.setPhone(reservationRequest.getPassengerPhone());
        passenger.setEmail(reservationRequest.getPassengerPhone());
        // persisting the passenger object
        LOGGER.info("Saving the passenger:" + passenger);
        Passenger savedPassenger = passengerRepository.save(passenger);

        // creating the reservation object
        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);
        reservation.setCheckedIn(false);
        reservation.setNumberOfBags(0);
        // persisting the reservation object
        LOGGER.info("Saving the reservation:" + reservation);
        Reservation savedReservation = reservationRepository.save(reservation);

        String filePath = ITINERARY_DIR + savedReservation.getId()
                + ".pdf";
        LOGGER.info("Generating  the itinerary");
        pdfGenerator.generateItinerary(savedReservation, filePath);
        LOGGER.info("Emailing the Itinerary");
        emailUtil.sendItinerary(passenger.getEmail(), filePath);
        return savedReservation;
    }


}
