package com.delivery.demo.core.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column()
    private String name;

    @Column()
    private float price;

    @ManyToOne()
    @JoinColumn(name = "userId")
    private User user;
}
