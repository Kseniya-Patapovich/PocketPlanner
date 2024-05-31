package com.pocketplanner.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity(name = "category")
public class Category {
    @Id
    @SequenceGenerator(name = "catSeqGen", sequenceName = "category_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "catSeqGen")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}
