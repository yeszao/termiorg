package org.termi.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.termi.common.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);
}