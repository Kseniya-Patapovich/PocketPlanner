package com.pocketplanner.service;

import com.pocketplanner.model.Goal;
import com.pocketplanner.model.User;
import com.pocketplanner.repository.GoalRepository;
import com.pocketplanner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class GoalServiceTest {
    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private GoalService goalService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
    }

    @Test
    public void testGetAllGoals() {
        Goal goal = new Goal();
        goal.setName("Test Goal");
        when(goalRepository.findAll()).thenReturn(List.of(goal));

        List<Goal> goals = goalService.getAllGoals();

        assertEquals(1, goals.size());
        assertEquals(goal.getName(), goals.get(0).getName());
    }

    @Test
    public void testGetGoalByIdFound() {
        Goal goal = new Goal();
        goal.setId(1L);
        when(goalRepository.findById(1L)).thenReturn(Optional.of(goal));

        Optional<Goal> foundGoal = goalService.getGoalById(1L);

        assertTrue(foundGoal.isPresent());
        assertEquals(goal.getId(), foundGoal.get().getId());
    }

    @Test
    public void testGetGoalByIdNotFound() {
        when(goalRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Goal> foundGoal = goalService.getGoalById(1L);

        assertFalse(foundGoal.isPresent());
    }

    @Test
    public void testGetByUserId() {
        Goal goal = new Goal();
        goal.setUser(user);
        when(goalRepository.findByUserId(1L)).thenReturn(List.of(goal));

        List<Goal> goals = goalService.getByUserId(1L);

        assertEquals(1, goals.size());
        assertEquals(user, goals.get(0).getUser());
    }

    @Test
    public void testDeleteGoal() {
        Goal goal = new Goal();
        goal.setId(1L);

        when(goalRepository.findById(1L)).thenReturn(Optional.of(goal));
        when(goalRepository.findById(1L)).thenReturn(Optional.empty());

        Boolean isDeleted = goalService.deleteGoal(1L);

        assertFalse(isDeleted);
    }
}
