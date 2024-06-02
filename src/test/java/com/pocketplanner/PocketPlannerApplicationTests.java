package com.pocketplanner;

import com.pocketplanner.controller.UserController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PocketPlannerApplicationTests {
    @Autowired
    UserController userController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(userController);
    }
}
