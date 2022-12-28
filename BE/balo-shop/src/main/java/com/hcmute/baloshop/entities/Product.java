package com.hcmute.baloshop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name="tbl_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long discountId;
    private Long categoryId;
    private Long branchId;
    private String name;
    private Long price;
    private Long standCost;
    private String image;
    private String description;
    private Boolean isDelete;
    @CreationTimestamp
    private Date createdDate;
    @UpdateTimestamp
    private Date updateDate;
}
