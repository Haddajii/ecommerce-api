package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.dtos.UpdateProductRequest;
import com.codewithmosh.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id" , target = "categoryId")
    ProductDto productToProductDto(Product product);
    Product toEntity(ProductDto productDto);
    void updateProduct(UpdateProductRequest request,@MappingTarget Product product);
}
