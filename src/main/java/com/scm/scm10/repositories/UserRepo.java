package com.scm.scm10.repositories;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.scm.scm10.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
