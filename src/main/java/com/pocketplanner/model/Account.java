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
@Entity(name = "accounts")
public class Account {
    @Id
    @SequenceGenerator(name = "accountSeqGen", sequenceName = "accounts_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "accountSeqGen")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
