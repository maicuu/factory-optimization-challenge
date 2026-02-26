package com.factory.opt.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.math.BigDecimal;

@Entity
@Table(name = "raw_materials")
public class RawMaterial extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 50)
    @Schema(examples = {"RM-WOOD"})
    public String code;

    @NotBlank
    @Column(nullable = false)
    @Schema(examples = {"Oak Wood"})
    public String name;

    @NotNull
    @DecimalMin(value = "0.0000")
    @Column(name = "stock_quantity", nullable = false, precision = 15, scale = 4)
    @Schema(examples = {"100.0000"})
    public BigDecimal stockQuantity;

    @NotBlank
    @Column(length = 10, nullable = false)
    @Schema(examples = {"kg"})
    public String unit;

    public RawMaterial() {}
}