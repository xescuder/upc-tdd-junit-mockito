package talent.upc.edu.booking.service;

import lombok.AllArgsConstructor;
import talent.upc.edu.booking.model.BookingRequest;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Arrays;

@AllArgsConstructor
public class BookingService {

    private TimeProvider timeProvider;

    public static final LocalDate[] BLACK_FRIDAY_DATES = {
            LocalDate.of(2025, Month.NOVEMBER, 28),
            LocalDate.of(2025, Month.NOVEMBER, 29),
            LocalDate.of(2025, Month.NOVEMBER, 30),
            LocalDate.of(2025, Month.NOVEMBER, 1),
            LocalDate.of(2025, Month.NOVEMBER, 2),
            LocalDate.of(2025, Month.NOVEMBER, 3)
    };

    public int calculateTotalPrice(BookingRequest bookingRequest) {
        Period period = Period.between(bookingRequest.getDateFrom(), bookingRequest.getDateTo());

        if (bookingRequest.getUser().getRegistrationDate() != null && bookingRequest.getUser().getRegistrationDate().isBefore(bookingRequest.getDateFrom().minusYears(1))) {
            return bookingRequest.getPricePerNight() * period.getDays() * 95 / 100;
        }

        if (Arrays.asList(BLACK_FRIDAY_DATES).contains(timeProvider.getCurrentDate())) {
            return bookingRequest.getPricePerNight() * period.getDays() * 85 / 100;
        }

        return bookingRequest.getPricePerNight() * period.getDays();
    }
}
