package talent.upc.edu.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talent.upc.edu.booking.model.User;

public interface UserRepository extends JpaRepository<User, Long> {}
