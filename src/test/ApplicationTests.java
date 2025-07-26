package com.scm.scm10;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.services.EmailService;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private EmailService service;

    void sendEmailTest() {
        service.sendEmail("sarimbinasif12345@gmail.com", "Just managing the emails",
                "this is scm project working on email service");
    }

    // void testUnits() {

    //     int result = 40;

    //     List<String> list = List.of("ram", "shyam", "ankit");

    //     assertThat(result).isEqualTo(50);

    //     assertThat(list).asList().size().isGreaterThan(5);

    // }

}
