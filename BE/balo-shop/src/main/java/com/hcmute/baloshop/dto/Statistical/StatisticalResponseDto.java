package com.hcmute.baloshop.dto.statistical;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticalResponseDto {
    private Long totalUser;
    private Long totalInvoice;
    private Long totalTurnover;
    private Long totalProduct;
}
