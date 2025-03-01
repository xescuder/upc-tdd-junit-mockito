package talent.upc.edu.booking.service;

import talent.upc.edu.booking.model.BookingRequest;

import java.time.Period;

public class BookingService {
    public int calculateTotalPrice(BookingRequest bookingRequest) {
        Period period = Period.between(bookingRequest.getDateFrom(), bookingRequest.getDateTo());
        return bookingRequest.getPricePerNight() * period.getDays();
    }
}
