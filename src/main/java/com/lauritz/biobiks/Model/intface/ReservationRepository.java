package com.lauritz.biobiks.Model.intface;

import com.lauritz.biobiks.Model.Reservation;

import java.util.Optional;

public interface ReservationRepository {

    Optional<Reservation> findById(int reservationId);

    void save(Reservation reservation);
}
