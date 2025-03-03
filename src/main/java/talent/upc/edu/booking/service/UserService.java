package talent.upc.edu.booking.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import talent.upc.edu.booking.exception.NotFoundException;
import talent.upc.edu.booking.model.User;
import talent.upc.edu.booking.repository.UserRepository;

@Service
public class UserService {
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User create(User user) {
    logger.debug("User create action called");
    return userRepository.save(user);
  }

  public User findOne(Long userId) {
    logger.debug("User findById action called");
    return userRepository
        .findById(userId)
        .orElseThrow(() -> new NotFoundException("User of id " + userId + " not found."));
  }

  public List<User> findAll() {
    logger.debug("User findAll action called");
    return userRepository.findAll();
  }

  public User update(Long userId, User user) {
    logger.debug("User update action called");
    User foundUser = findOne(userId);



    logger.debug("User found");

    foundUser.setFirstName(user.getFirstName());
    foundUser.setLastName(user.getLastName());
    foundUser.setEmail(user.getEmail());
    userRepository.save(foundUser);

    logger.debug("User updated successfully");
    return foundUser;
  }

  public void delete(Long userId) {
    logger.debug("User delete action called");
    userRepository.deleteById(userId);
  }

  public Optional<User> getUser(Long any) {
    return userRepository.findById(any);
  }
}
