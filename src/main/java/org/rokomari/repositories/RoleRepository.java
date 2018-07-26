package org.rokomari.repositories;

import org.rokomari.models.Role;
import org.rokomari.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Abdullah Al Amin on 7/26/2018.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(RoleName roleName);
}
