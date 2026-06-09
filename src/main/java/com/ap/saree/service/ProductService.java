package com.ap.saree.service;

import com.ap.saree.dto.SareeProductDTO;
import com.ap.saree.entity.Product;
import com.ap.saree.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<SareeProductDTO> getAllCatalogProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SareeProductDTO convertToDTO(Product product) {
        return new SareeProductDTO(
                product.getId(),
                product.getTitle(),
                product.getFabric(),
                product.getPrice(),
                product.getDiscountPrice(),
                product.getPrimaryImage(),
                product.getHoverImage(),
                product.getIsExclusive(),
                product.getStockCount(),
                product.getColors()
        );
    }
}
