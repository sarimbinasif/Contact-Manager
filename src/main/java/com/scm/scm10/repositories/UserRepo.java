package com.scm.scm10.repositories;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.scm.scm10.entities.User;

// @Repository
// public class UserRepo extends JpaRepository<User, String>{
//     //  auto implemented by spring data jpa
//     Optional<User> findByEmail(String email);
//     // extra methods related to DB

//     // custom query methods
//     // custom finder methods

// }



// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
// import com.scm.scm10.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
