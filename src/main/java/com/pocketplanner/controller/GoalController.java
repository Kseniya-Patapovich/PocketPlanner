package com.pocketplanner.controller;

import com.pocketplanner.exception.CustomValidException;
import com.pocketplanner.model.Goal;
import com.pocketplanner.model.dto.GoalCreateDto;
import com.pocketplanner.model.dto.TransactionCreateDto;
import com.pocketplanner.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> createGoal(@RequestBody GoalCreateDto goalCreateDto, @PathVariable("id") Long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidException(bindingResult.getAllErrors().toString());
        }
        return new ResponseEntity<>(goalService.createGoal(goalCreateDto, id) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGoal(@PathVariable("id") Long id) {
        return new ResponseEntity<>(goalService.deleteGoal(id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateGoal(@RequestBody TransactionCreateDto amount, @PathVariable("id") Long id) {
        return new ResponseEntity<>(goalService.updateGoal(amount.getAmount(), id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}
