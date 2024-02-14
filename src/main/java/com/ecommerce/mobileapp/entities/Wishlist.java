package com.ecommerce.mobileapp.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;


@Entity
public class Wishlist {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int Id;
    @ManyToMany
    private List<Product> product = new ArrayList<>();
    
    public int getId() {
        return Id;
    }
    public List<Product> getProduct() {
        return product;
    }
    public void setProduct(List<Product> product) {
        this.product = product;
    }
    
}
