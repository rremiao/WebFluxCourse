package com.rremiao.productservice.util;

import org.modelmapper.ModelMapper;

import com.rremiao.productservice.dto.ProductDTO;
import com.rremiao.productservice.entity.Product;

public class EntityDTOUtil {
    
    public static ProductDTO toDto(Product product) {
        ProductDTO dto = new ProductDTO();
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.map(product, dto);

        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        Product product = new Product();
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.map(dto, product);

        return product;
    }
}
