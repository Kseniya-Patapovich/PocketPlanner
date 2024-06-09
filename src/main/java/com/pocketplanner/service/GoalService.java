package com.pocketplanner.service;

import com.pocketplanner.model.Account;
import com.pocketplanner.model.Goal;
import com.pocketplanner.model.Status;
import com.pocketplanner.model.User;
import com.pocketplanner.model.dto.GoalCreateDto;
import com.pocketplanner.repository.AccountRepository;
import com.pocketplanner.repository.GoalRepository;
import com.pocketplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalService {
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository, UserRepository userRepository, AccountRepository accountRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    public Optional<Goal> getGoalById(Long id) {
        return goalRepository.findById(id);
    }

    public List<Goal> getByUserId(Long userId) {
        return goalRepository.findByUserId(userId);
    }

    public Boolean createGoal(GoalCreateDto goalCreateDto, Long userId) {
        Goal goal = new Goal();
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return false;
        }
        goal.setUser(userOptional.get());
        goal.setName(goalCreateDto.getName());
        goal.setTargetAmount(goalCreateDto.getTargetAmount());
        goal.setCurrentAmount(0.0);
        Goal createdGoal = goalRepository.save(goal);
        return getGoalById(createdGoal.getId()).isPresent();
    }

    public Boolean deleteGoal(Long id) {
        Optional<Goal> checkGoal = getGoalById(id);
        if (checkGoal.isEmpty()) {
            return false;
        }
        goalRepository.deleteById(id);
        return getGoalById(id).isEmpty();
    }

    public Boolean updateGoal(Double amount, Long id, Long accountId) {
        Optional<Goal> goalOptional = goalRepository.findById(id);
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (goalOptional.isPresent() && accountOptional.isPresent()) {
            Goal goal = goalOptional.get();
            goal.setTargetAmount(goal.getTargetAmount() - amount);
            goal.setCurrentAmount(goal.getCurrentAmount() + amount);
            if (goal.getTargetAmount() == 0.0) {
                goal.setStatus(Status.COMPLETED);
            }
            Account account = accountOptional.get();
            account.setBalance(account.getBalance() - amount);
            Goal updateGoal = goalRepository.saveAndFlush(goal);
            return goal.equals(updateGoal);
        }
        return false;
    }
}
