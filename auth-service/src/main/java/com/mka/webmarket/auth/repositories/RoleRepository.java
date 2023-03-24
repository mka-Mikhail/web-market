package com.mka.webmarket.auth.repositories;

import com.mka.webmarket.auth.entities.Role;
import com.mka.webmarket.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<User> findByName(String name);
}
