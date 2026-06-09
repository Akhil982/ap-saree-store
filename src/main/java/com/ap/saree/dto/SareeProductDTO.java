package com.ap.saree.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SareeProductDTO {
    private String id;
    private String title;
    private String fabric;
    private Double price;
    private Double discountPrice;
    private String primaryImage;
    private String hoverImage;
    private Boolean isExclusive;
    private Integer stockCount;
    private List<String> colors; // List of Hex strings
}
