package com.pocketplanner.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GoalCreateDto {
    private String name;
    private Double targetAmount;
}
