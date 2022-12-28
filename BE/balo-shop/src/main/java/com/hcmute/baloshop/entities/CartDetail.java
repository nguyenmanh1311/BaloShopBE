package com.hcmute.baloshop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="tbl_cartDetail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long cartId;
    private Long quantity;
    private Long price;
    @CreationTimestamp
    private Date createdDate;
    @UpdateTimestamp
    private Date updateDate;
}
