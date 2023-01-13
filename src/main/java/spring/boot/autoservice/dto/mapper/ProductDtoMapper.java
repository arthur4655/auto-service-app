package spring.boot.autoservice.dto.mapper;

import org.springframework.stereotype.Component;
import spring.boot.autoservice.dto.request.ProductRequestDto;
import spring.boot.autoservice.dto.response.ProductResponseDto;
import spring.boot.autoservice.model.Product;

@Component
public class ProductDtoMapper {
    public Product toModel(ProductRequestDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return product;
    }

    public ProductResponseDto toDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        return dto;
    }
}
