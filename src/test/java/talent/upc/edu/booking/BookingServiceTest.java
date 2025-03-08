package talent.upc.edu.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import talent.upc.edu.booking.adapters.UserRestAdapter;
import talent.upc.edu.booking.model.Booking;
import talent.upc.edu.booking.model.User;
import talent.upc.edu.booking.repository.BookingRepository;
import talent.upc.edu.booking.service.*;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @Mock
    private RoomService roomService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserService userService;

    @Mock
    private MailSender mailSender;

    @InjectMocks
    private BookingService bookingService;


    @Captor
    private ArgumentCaptor<Booking> bookingArgumentCaptor;


    @Test
    void should_MakeBooking_When_AllInputIsCorrect() {
        // Given
        Booking booking = Booking.builder().guestFullName("Xavier Escudero Sabadell").build();
        User user = User.builder().build();
        LocalDate checkInDate = LocalDate.of(2025, 3, 21);
        LocalDate checkOutDate = LocalDate.of(2025, 3, 25);
        int totalGuests = 3;

        when(roomService.findAvailableRoomId(checkInDate, checkOutDate, totalGuests)).thenReturn(1L);
        when(userService.getUser(any(Integer.class))).thenReturn(user);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking bookingToSave = invocation.getArgument(0);
            long bookingId = 1L + (long) (Math.random() * (10L - 1L));
            bookingToSave.setId(bookingId);
            return bookingToSave;
        });

        // when
        String bookingId = bookingService.makeBooking(booking);

        // then
        verify(bookingRepository).save(bookingArgumentCaptor.capture());
        Booking savedBooking = bookingArgumentCaptor.getValue();
        assertThat(booking.getGuestFullName()).equals(savedBooking.getGuestFullName());
        verify(mailSender).sendMail(user.getEmail(), "Booking Confirmation", "Your booking has been confirmed with id: " + bookingId);
    }
}
