package com.hcmute.baloshop.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDTO {
    private Long userId;
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
