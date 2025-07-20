package com.codewithmosh.store.dtos;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequest {

    @NotNull(message = "quantity must be provided")
    @Min(value = 1 , message = "quantity must be greater than 0")
    @Max(value = 1000 , message = "maximum capacity is 1000")
    private Integer quantity;
}
