package com.ap.saree.repository;

import com.ap.saree.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    // Interacts with your production database cleanly out-of-the-box
}
