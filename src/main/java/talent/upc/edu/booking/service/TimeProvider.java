package talent.upc.edu.booking.service;

import java.time.LocalDate;

public class TimeProvider {
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
