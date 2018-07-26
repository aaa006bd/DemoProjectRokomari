package org.rokomari.repositories;

import org.rokomari.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Abdullah Al Amin on 7/26/2018.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
