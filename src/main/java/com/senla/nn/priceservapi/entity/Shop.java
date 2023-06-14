package com.senla.nn.priceservapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shops")
@Builder
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "shops_to_products",
            joinColumns = @JoinColumn(name = "shops_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();
}
