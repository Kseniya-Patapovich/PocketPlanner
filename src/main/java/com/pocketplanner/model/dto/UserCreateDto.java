package com.pocketplanner.model.dto;

import com.pocketplanner.annotation.Adult;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserCreateDto {
    @NotNull
    private String username;

    @NotNull
    @Adult
    private Integer age;

    @NotNull
    private String userPassword;

    @NotNull
    private String userLogin;
}
