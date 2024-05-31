package com.pocketplanner.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity(name = "goals")
public class Goal {
    @Id
    @SequenceGenerator(name = "goalSeqGen", sequenceName = "goals_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "goalSeqGen")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "target_amount", nullable = false)
    private BigDecimal targetAmount;

    @Column(name = "current_amount", nullable = false)
    private BigDecimal currentAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
