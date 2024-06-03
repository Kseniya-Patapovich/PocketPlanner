package com.pocketplanner.repository;

import com.pocketplanner.model.Goal;
import com.pocketplanner.model.Status;
import com.pocketplanner.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GoalRepositoryTest {
    @Autowired
    GoalRepository goalRepository;
    static Goal goal;

    @BeforeAll
    static void beforeAll() {
        goal = new Goal();
        goal.setName("Buy a car");
        goal.setTargetAmount(1000.0);
        goal.setCurrentAmount(200.0);
        goal.setStatus(Status.IN_PROCESS);
        goal.setUser(new User());
    }

    @Test
    void findAllTest_Success() {
        List<Goal> goals = goalRepository.findAll();
        Assertions.assertNotNull(goals);
    }

    @Test
    void findByIdTest_Success() {
        Goal goalFromDb = goalRepository.findAll().get(0);
        Optional<Goal> goalOptional = goalRepository.findById(goalFromDb.getId());
        Assertions.assertTrue(goalOptional.isPresent());
    }
}
