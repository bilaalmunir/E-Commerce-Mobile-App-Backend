package com.ecommerce.mobileapp.entities;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
//book details 
@Entity
@Table(name="Userr")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int userID;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private long balance = 64738264;
    @OneToMany
    private List<Product> ownedCars=new ArrayList<>();
    @OneToMany
    private List<Product> carsAdded= new ArrayList<>();
    @OneToOne
    private Wishlist wishlist;
    
    public List<Product> getCarsAdded() {
        return carsAdded;
    }
    public void setCarsAdded(List<Product> carsAdded) {
        this.carsAdded = carsAdded;
    }
    public List<Product> getOwnedCars() {
        return ownedCars;
    }
    public void setOwnedCars(List<Product> ownedCars) {
        this.ownedCars = ownedCars;
    }
  
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID =  userID;
    }
  
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Wishlist getWishlist() {
        return wishlist;
    }
    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }
    public long getBalance() {
        return balance;
    }
    public void setBalance(long balance) {
        this.balance = balance;
    }
    
    
   }
    


