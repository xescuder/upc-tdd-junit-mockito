package talent.upc.edu.booking.service;

import lombok.AllArgsConstructor;
import talent.upc.edu.booking.model.Booking;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Arrays;

@AllArgsConstructor
public class PricingService {

    private TimeProvider timeProvider;

    public static final LocalDate[] BLACK_FRIDAY_DATES = {
            LocalDate.of(2025, Month.NOVEMBER, 28),
            LocalDate.of(2025, Month.NOVEMBER, 29),
            LocalDate.of(2025, Month.NOVEMBER, 30),
            LocalDate.of(2025, Month.NOVEMBER, 1),
            LocalDate.of(2025, Month.NOVEMBER, 2),
            LocalDate.of(2025, Month.NOVEMBER, 3)
    };

    public double calculateTotalPrice(Booking bookingRequest) {
        Period period = Period.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());

        if (bookingRequest.getUser().getRegistrationDate() != null && bookingRequest.getUser().getRegistrationDate().isBefore(bookingRequest.getCheckInDate().minusYears(1))) {
            return bookingRequest.getRoom().getPricePerNight() * period.getDays() * 95 / 100;
        }

        if (Arrays.asList(BLACK_FRIDAY_DATES).contains(timeProvider.getCurrentDate())) {
            return bookingRequest.getRoom().getPricePerNight() * period.getDays() * 85 / 100;
        }

        return bookingRequest.getRoom().getPricePerNight() * period.getDays();
    }
}
