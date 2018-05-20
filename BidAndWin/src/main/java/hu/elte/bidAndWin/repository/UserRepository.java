package hu.elte.bidAndWin.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import hu.elte.bidAndWin.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);
}
