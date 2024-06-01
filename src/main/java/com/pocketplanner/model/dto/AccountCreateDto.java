package com.pocketplanner.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
public class AccountCreateDto {
    private String name;
    private Double balance;
}
