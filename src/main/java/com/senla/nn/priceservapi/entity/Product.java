package com.senla.nn.priceservapi.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "products",
        uniqueConstraints = @UniqueConstraint(name = "UniqueProductConstraint", columnNames = {"name","brands_id"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "created_date")
    @CreatedDate
    @ColumnDefault(value="LOCAL_TIMESTAMP")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    @ColumnDefault(value="LOCAL_TIMESTAMP")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "brands_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Category category;

    @ManyToMany(mappedBy = "products")
    @ToString.Exclude
    private List<Shop> shops = new ArrayList<>();
}
