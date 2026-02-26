package com.factory.opt.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@Table(name = "raw_materials")
public class RawMaterial extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Schema(examples = {"RM-WOOD"})
    public String code;

    @NotBlank
    @Column(nullable = false)
    @Schema(examples = {"Oak Wood"})
    public String name;

    @NotNull
    @Column(name = "stock_quantity", nullable = false)
    @Schema(examples = {"100.0"})
    public Double stockQuantity;

    @NotBlank
    @Column(length = 10, nullable = false)
    @Schema(examples = {"kg"})
    public String unit;

    public RawMaterial() {}
}