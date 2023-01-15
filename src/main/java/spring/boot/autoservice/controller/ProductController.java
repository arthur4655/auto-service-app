package spring.boot.autoservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.autoservice.dto.mapper.ProductDtoMapper;
import spring.boot.autoservice.dto.request.ProductRequestDto;
import spring.boot.autoservice.dto.response.ProductResponseDto;
import spring.boot.autoservice.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductDtoMapper dtoMapper;

    public ProductController(ProductService productService, ProductDtoMapper dtoMapper) {
        this.productService = productService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    @Operation(summary = "Add new product to db")
    public ProductResponseDto add(@Parameter(description = "Provide the next fields in "
                + "JSON format: name, price") @RequestBody ProductRequestDto requestDto) {
        return dtoMapper.toDto(productService.save(dtoMapper.toModel(requestDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product with id")
    public ProductResponseDto update(@Parameter(description = "product id") @PathVariable Long id,
                                     @Parameter(description = "Provide the next fields in "
                                             + "JSON format: name, price")
                                     @RequestBody ProductRequestDto requestDto) {
        return dtoMapper.toDto(productService.update(id, dtoMapper.toModel(requestDto)));
    }
}
