package com.globant.academy.repository;


import com.globant.academy.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("SELECT p FROM Product p " +
            "JOIN p.cartItemList ci " +
            "JOIN ci.cart c " +
            "WHERE c.id = :cartId")
    public List<Product> getProductsByCartId(@Param("cartId") Integer cartId);
}
