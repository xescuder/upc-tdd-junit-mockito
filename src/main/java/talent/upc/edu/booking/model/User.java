package talent.upc.edu.booking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class User {

  private int id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private LocalDate registrationDate;
  private String avatar;
}
