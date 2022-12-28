package com.hcmute.baloshop.dto.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageProductResponseDto {
    private String id;
    private String path;
    private Long productId;
}
