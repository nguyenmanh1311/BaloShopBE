package com.hcmute.baloshop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="tbl_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String addressLine;
    private String ward;
    private String district;
    private String province;
    private Long userId;
    private Long provinceId;
    private Long districtId;
    private String wardId;
    private String fullName;
    private String phone;
    private Boolean isDelete;
    @CreationTimestamp
    private Date createdDate;
    @UpdateTimestamp
    private Date updateDate;

}
