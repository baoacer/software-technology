package com.example.jwt.entities;

import java.time.LocalDate;
import org.hibernate.annotations.UpdateTimestamp;
import com.example.jwt.utils.InventoryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "inventory")
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SMALLINT UNSIGNED")
    private short id;

    @Min(0) // make sure quantity is not negative
    @Column(nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    private short quantity;

    @Enumerated(EnumType.STRING) // Store the enum as a string in the database
    @Column(nullable = false)
    private InventoryStatus status = InventoryStatus.IN_STOCK; // Default value

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}
