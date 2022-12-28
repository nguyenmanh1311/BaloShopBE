package com.hcmute.baloshop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="tbl_invoices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cartId;
    private Long userId;
    private Long grandTotal;
    private String status;
    private Long addressId;
    private Boolean isPayment;
    @CreationTimestamp
    private Date createdDate;
    @UpdateTimestamp
    private Date updateDate;

}
