package com.pocketplanner.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GoalCreateDto {
    @NotNull
    private String name;

    @NotNull
    private Double targetAmount;
}
