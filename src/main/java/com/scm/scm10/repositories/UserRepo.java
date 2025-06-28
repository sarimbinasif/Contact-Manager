package com.scm.scm10.repositories;

import org.springframework.stereotype.Repository;

import com.scm.scm10.entities.User;

@Repository
public class UserRepo extends JpaRepository<User, String>{
    // extra methods related to DB
// custom query methods
// custom finder methods

}
