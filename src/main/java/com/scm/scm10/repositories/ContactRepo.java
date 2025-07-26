// package com.scm.scm10.repositories;

// import org.springframework.stereotype.Repository;

// import com.scm.scm10.entities.Contact;
// // import com.scm.scm10.entities.User;
// import com.scm.scm10.entities.User;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

// import java.util.List;


// @Repository
// public interface ContactRepo extends JpaRepository<Contact, String>{

//     //______ find contact by user_______

//     // custom finder method
//     // List<Contact> findByUser(User user);

//     // custom query method
//     @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
//     List<Contact> findByUserId(@Param("userId") String userid);

//     Page<Contact> getByUser(User user, PageRequest pageable);

//     Page<Contact> findByUserAndEmailContaining(User user, String emailKeyword, PageRequest pageable);



// }



// package com.scm.repsitories;
package com.scm.scm10.repositories;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// import com.scm.entities.Contact;
// import com.scm.entities.User;

 import com.scm.scm10.entities.Contact;
import com.scm.scm10.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {
    // find the contact by user
    // custom finder method
    Page<Contact> findByUser(User user, Pageable pageable);

    // custom query method
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

    Page<Contact> findByUserAndNameContaining(User user, String namekeyword, Pageable pageable);

    Page<Contact> findByUserAndEmailContaining(User user, String emailkeyword, Pageable pageable);

    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phonekeyword, Pageable pageable);

}