package com.pocketplanner.model.dto;

import com.pocketplanner.annotation.Adult;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserUpdateAgeDto {
    @NotNull
    @Adult
    private Integer age;
}
