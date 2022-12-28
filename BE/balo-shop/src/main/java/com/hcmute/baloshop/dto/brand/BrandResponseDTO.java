package com.hcmute.baloshop.dto.brand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponseDTO {
    private Long id;
    private String name;
    private String description;
}
