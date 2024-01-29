package com.ecommerce.mobileapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.mobileapp.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findByStatus(Boolean status);
   

}
