package com.ecommerce.mobileapp.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.mobileapp.entities.User;

@Repository
public interface Userrepository extends JpaRepository<User,Long>{
    public User findByUsername(String username);
}