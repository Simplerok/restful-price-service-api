package com.senla.nn.priceservapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brands", uniqueConstraints = @UniqueConstraint(columnNames ={"name"}))
@Builder
public class Brand {
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

    @JsonIgnore
    @OneToMany(mappedBy = "brand")
    @ToString.Exclude
    private List<Product> product;

}
