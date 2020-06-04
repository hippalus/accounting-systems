package com.hippalus.accountingsystem.domain.models;

import com.hippalus.accountingsystem.domain.commands.ProductCreateCommand;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
public class Product {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private String name;

    @NotNull
    @Column(name = "pirice")
    private Money price;

    @Builder
    private Product(Long id, String name, Money price) {
        checkArguments(name, price);
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static Product create(ProductCreateCommand cmd) {
        return Product.builder()
                .name(cmd.getName())
                .price(Money.of(cmd.getPrice()))
                .build();

    }

    private void checkArguments(String name, Money price) {
        if (isEmpty(name)) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (isNull(price)) {
            throw new IllegalArgumentException("Product price is required");
        }
    }
}