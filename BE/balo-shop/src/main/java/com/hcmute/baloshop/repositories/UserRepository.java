package com.hcmute.baloshop.repositories;

import com.hcmute.baloshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByPhone(String phone);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long userId);

    @Query(value = "SELECT * FROM tbl_user p WHERE p.role_id = 1", nativeQuery = true)
    List<User> findAllCustomer();
    @Query(value = "SELECT * FROM tbl_user p WHERE p.role_id = 2 AND p.id != :id", nativeQuery = true)
    List<User> findAllAdmin(@Param("id")Long id);

    @Query(value = "SELECT * FROM tbl_user p WHERE p.email = :email OR p.phone = :phone", nativeQuery = true)
    List<User> validateUser(@Param("email")String email,
                            @Param("phone")String phone);


}
