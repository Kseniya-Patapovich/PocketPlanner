package com.pocketplanner.security.model.dto;

import com.pocketplanner.annotation.Adult;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RegistrationDto {
    private String login;
    private String password;
    private String username;

    @Adult
    private Integer age;
}
