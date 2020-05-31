package com.hippalus.accountingsystem.application.mappers;

import com.hippalus.accountingsystem.application.responses.ProductResponse;
import com.hippalus.accountingsystem.domain.models.Product;

public interface ProductMapper {

    static ProductResponse productToProductRes(Product product) {
        if (product == null) {
            return null;
        } else {
            ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();
            productResponse.id(product.getId());
            productResponse.name(product.getName());
            productResponse.price(MoneyMapper.moneyToDecimal(product.getPrice()));
            return productResponse.build();
        }
    }
    static Product productResToProduct(ProductResponse response) {
        if (response == null) {
            return null;
        } else {
            var product = Product.builder();
            product.id(response.getId());
            product.name(response.getName());
            product.price(MoneyMapper.decimalToMoney(response.getPrice()));
            return product.build();
        }
    }

}
