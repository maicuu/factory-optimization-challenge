package com.factory.opt.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank(message = "O código é obrigatório")
    @Column(nullable = false, unique = true, length = 50)
    @Schema(description = "Código único", examples = {"PRD-001"}) 
    public String code;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false)
    @Schema(description = "Nome do produto", examples = {"Luxury Desk"}) 
    public String name;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser positivo")
    @Column(nullable = false, precision = 15, scale = 2)
    @Schema(description = "Preço de venda", examples = {"850.00"}) 
    public BigDecimal price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<ProductComposition> compositions = new ArrayList<>();

    public Product() {}
}