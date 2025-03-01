package talent.upc.edu.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import talent.upc.edu.booking.model.BookingRequest;
import talent.upc.edu.booking.model.User;
import talent.upc.edu.booking.service.BookingService;
import talent.upc.edu.booking.service.TimeProvider;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static talent.upc.edu.booking.service.BookingService.BLACK_FRIDAY_DATES;

/**
 * Unit tests for the BookingService class.
 * User Story: Booking Price Calculation
 * As a Customer looking to book a stay
 * I want to Receive an accurate price calculation for my booking
 * So that I can know the total cost before confirming my reservation
 * Acceptance Criteria:
 * The total price is calculated by multiplying the number of nights by the price per night.
 * If the user has been registered for more than 1 year, they receive a 5% discount on the total booking price.
 * If the booking is made during Black Friday (between November 28 and December 3), a 15% discount will be applied to the total booking price.
 * If the user qualifies for both the Christmas discount and a new user or loyalty discount, only the highest discount is applied (not cumulative)
 */
public class BookingServiceTest {
    private TimeProvider timeProvider;
    private BookingService bookingService;

    @BeforeEach
    void setup() {
        this.timeProvider = mock(TimeProvider.class);
        this.bookingService = new BookingService(timeProvider);
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

    @Test
    void should_ReturnDiscountedTotalPrice_When_UserRegisteredMoreThanOneYear() {
        // Given
        LocalDate registrationDate = LocalDate.now().minusYears(1).minusDays(1);
        User user = new User(1L, "Xavier", "Escudero", "", registrationDate);
        LocalDate dateFrom = LocalDate.of(2025, 4, 21);
        LocalDate dateTo = LocalDate.of(2025, 4, 25);
        BookingRequest bookingRequest = new BookingRequest(user, dateFrom, dateTo, 100);

        int expected = Period.between(dateFrom, dateTo).getDays() * bookingRequest.getPricePerNight() * 95 / 100;

        // When
        int actual = bookingService.calculateTotalPrice(bookingRequest);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_ReturnDiscountedTotalPrice_When_BookingIsDuringBlackFriday() {
        // Given
        User user = new User(1L, "Xavier", "Escudero", "", null);
        LocalDate dateFrom = LocalDate.of(2026, Month.FEBRUARY, 5);
        LocalDate dateTo = LocalDate.of(2026, Month.FEBRUARY, 9);
        BookingRequest bookingRequest = new BookingRequest(user, dateFrom, dateTo, 100);

        when(this.timeProvider.getCurrentDate()).thenReturn(Arrays.asList(BLACK_FRIDAY_DATES).get(1));
        int expected = Period.between(dateFrom, dateTo).getDays() * bookingRequest.getPricePerNight() * 85 / 100;

        // When
        int actual = bookingService.calculateTotalPrice(bookingRequest);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
