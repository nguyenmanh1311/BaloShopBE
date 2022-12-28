package com.hcmute.baloshop.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestUpdateDTO {
    private String addressLine;
    private String ward;
    private String district;
    private String province;
    private String fullName;
    private String phone;
    private Long provinceId;
    private Long districtId;
    private String wardId;
}
