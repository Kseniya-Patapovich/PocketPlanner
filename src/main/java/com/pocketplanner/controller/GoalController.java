package com.pocketplanner.controller;

import com.pocketplanner.model.Goal;
import com.pocketplanner.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/goal")
public class GoalController {
    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public ResponseEntity<List<Goal>> getAllGoals() {
        return new ResponseEntity<>(goalService.getAllGoals(), HttpStatus.OK);
    }

    @GetMapping("/userId/{id}")
    public ResponseEntity<List<Goal>> getByUserId(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(goalService.getByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable("id") Long id) {
        Optional<Goal> goal = goalService.getGoalById(id);
        if (goal.isPresent()) {
            return new ResponseEntity<>(goal.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
