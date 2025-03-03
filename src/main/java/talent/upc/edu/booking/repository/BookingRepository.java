package talent.upc.edu.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talent.upc.edu.booking.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
