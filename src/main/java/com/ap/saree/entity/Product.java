package com.ap.saree.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "fabric", nullable = false, length = 100)
    private String fabric;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "discountprice")
    private Double discountPrice;

    @Column(name = "primaryimage", nullable = false, length = 1000)
    private String primaryImage;

    @Column(name = "hoverimage", length = 1000)
    private String hoverImage;

    @Column(name = "isexclusive")
    private Boolean isExclusive = false;

    @Column(name = "stockcount", nullable = false)
    private Integer stockCount;

    // 💡 FIXED: Maps directly to Prisma's native PostgreSQL text[] column
    // No extra table joins needed!
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "colors", columnDefinition = "text[]")
    private List<String> colors;
}