package com.ecommerce.mobileapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.mobileapp.entities.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    
}
