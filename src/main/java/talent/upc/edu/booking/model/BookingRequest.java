package talent.upc.edu.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private User user;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int pricePerNight;
}
