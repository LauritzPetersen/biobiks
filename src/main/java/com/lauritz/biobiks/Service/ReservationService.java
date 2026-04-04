package com.lauritz.biobiks.Service;

import com.lauritz.biobiks.Model.Reservation;
import com.lauritz.biobiks.Model.Snack;
import com.lauritz.biobiks.Model.User;
import com.lauritz.biobiks.Model.intface.ReservationRepository;
import com.lauritz.biobiks.Service.validation.ReservationValidation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    // Vi trækker vores Repository og Validering ind (Dependency Injection)
    private final ReservationRepository reservationRepository;
    private final ReservationValidation validation;

    public ReservationService(ReservationRepository reservationRepository, ReservationValidation validation) {
        this.reservationRepository = reservationRepository;
        this.validation = validation;
    }


    public List<String> createReservation(User user, Reservation reservation) {
        List<String> errors = new ArrayList<>();

        validation.validateNewReservation(user, reservation.getMovies(), errors);

        if (!errors.isEmpty()) {
            return errors;
        }

        applyComboDiscount(reservation);

        reservationRepository.save(reservation);

        return errors;
    }


    private double applyComboDiscount(Reservation reservation) {
        List<Snack> popcorns = new ArrayList<>();
        List<Snack> sodas = new ArrayList<>();

        for (Snack snack : reservation.getSnacks()) {
            if (snack.getType().equals("Popcorn")) {
                popcorns.add(snack);
            } else if (snack.getType().equals("Sodavand")) {
                sodas.add(snack);
            }
        }

        double totalDiscount = 0.0;

        // 2. Vi tager én popcorn ad gangen og leder efter en matchende sodavand
        for (Snack popcorn : popcorns) {
            for (int i = 0; i < sodas.size(); i++) {
                Snack soda = sodas.get(i);

                if (popcorn.getSize().equals(soda.getSize())) {
                    // Udregn 20% af lige præcis DENNE ene menu
                    double menuPrice = popcorn.getPrice() + soda.getPrice();
                    totalDiscount += (menuPrice * 0.20);
                    // Fjern sodavanden fra listen, så den ikke bliver parret med en anden popcorn
                    sodas.remove(i);
                    // Stop med at lede efter sodavand til denne popcorn (hop videre til næste popcorn)
                    break;
                }
            }
        }

        // 3. Hvis vi fandt nogen menuer, trækker vi den samlede rabat fra totalprisen
        if (totalDiscount > 0) {
            double currentTotal = reservation.getTotalPrice();
            reservation.setTotalPrice(currentTotal - totalDiscount);
        }
        return totalDiscount;
    }
}