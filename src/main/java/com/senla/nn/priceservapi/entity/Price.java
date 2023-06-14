package com.senla.nn.priceservapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prices",
        uniqueConstraints = @UniqueConstraint(name = "UniquePriceConstraint", columnNames = {"value","created_date","shops_id","products_id"}))
@Builder
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal value;

    @Column(name = "created_date")
    @CreatedDate
    @ColumnDefault(value="LOCAL_TIMESTAMP")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "shops_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "products_id")
    private Product product;
}
