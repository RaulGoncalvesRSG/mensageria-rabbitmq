package com.raul.commons.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {

    private String id;
    private String name;
    private Double price;
}
