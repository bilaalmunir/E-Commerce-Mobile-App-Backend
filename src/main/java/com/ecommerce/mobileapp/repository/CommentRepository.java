package com.ecommerce.mobileapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.mobileapp.entities.Comments;

@Repository
public interface CommentRepository extends JpaRepository<Comments,Integer> {

}