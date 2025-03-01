package talent.upc.edu.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import talent.upc.edu.booking.model.User;
import talent.upc.edu.booking.service.BookingService;

import java.time.LocalDate;
import java.time.Period;

import static org.assertj.core.api.Assertions.assertThat;

public class BookingServiceTest {
    private BookingService bookingService;

    @BeforeEach
    void setup() {
        this.bookingService = new BookingService();
    }

    @Test
    void should_ReturnCorrectTotalPrice_When_MultipleNightsAreBooked() {
        // Given
        User user = new User(1L, "Xavier", "Escudero", "xescuder@gmail.com", null);
        LocalDate dateFrom = LocalDate.of(2025, 4, 21);
        LocalDate dateTo = LocalDate.of(2025, 4, 25);
        BookingRequest bookingRequest = new BookingRequest(user, dateFrom, dateTo, 100);
        int expected = Period.between(dateFrom, dateTo).getDays() * bookingRequest.getPricePerNight();

        // When
        int actual = bookingService.calculateTotalPrice(bookingRequest);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
