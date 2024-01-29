package com.ecommerce.mobileapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.mobileapp.entities.Product;
import com.ecommerce.mobileapp.entities.User;
import com.ecommerce.mobileapp.entities.Wishlist;
import com.ecommerce.mobileapp.repository.ProductRepository;
import com.ecommerce.mobileapp.repository.Userrepository;
import com.ecommerce.mobileapp.repository.WishlistRepository;


import java.util.List;

@Service

public class ProductServices {
   @Autowired
   private ProductRepository proRepo;
   @Autowired
   private Userrepository userRepo;
   @Autowired
   private WishlistRepository wishlistRepo;

   public Product addCar(String carName,String model,String color,int userId) {
      Product Carinfo =  new Product(carName,model,color,userId);
      proRepo.save(Carinfo);
      return Carinfo;
   }

   public ResponseEntity<String> deleteCar(int carId){
            proRepo.deleteById(carId);
            return ResponseEntity.ok("Car has been deleted!");
   }

   public Product updateDetails(int userId,int carId, String carName,String model,String color ){
      Product product= proRepo.findById(carId).orElseThrow(() -> new IllegalArgumentException("no product found!"));
      product.setCarName(carName);
      product.setColor(color);
      product.setModel(model);
      proRepo.save(product);
      return product;

   }
   public Product buyCar (int ID, int userID){      
        Product carOptional = proRepo.findById(ID).orElseThrow(() -> new IllegalArgumentException("no product found"));
        int carPublishedBy = carOptional.getPublishedBy();
        System.out.println("car Published by Id"+carPublishedBy);
        System.out.println(userID);
             if (carOptional.getPublishedBy() != userID){
                //Product car = carOptional; // Retrieve the Product object
                carOptional.setStatus(true); 
                carOptional.setBoughtBy(userID);
                        User userOptional = userRepo.findById((long) userID).orElseThrow(() -> new IllegalArgumentException("no user found!"));
                         //User user= userOptional.get();
                          List<Product> ownedCars = userOptional.getOwnedCars();
                          ownedCars.add(carOptional);
                          System.out.println("saved");
           proRepo.save(carOptional);
                          userRepo.save(userOptional);   
                           return carOptional;
                }
            return null;     
            }
           
     public List<Product> getAllUnsoldCars(){
       return proRepo.findByStatus(false);
    }    
    
    public List<Product> getAllSoldCars(){
       return proRepo.findByStatus(true);
    }  

    public List<Product> getAllCars(){
        return proRepo.findAll();
    }

   public ResponseEntity<?> setWishlistItem(int userId, int productId){
      User user= userRepo.findById((long)userId).orElseThrow(() -> new IllegalArgumentException("no user found!"));
      Product product= proRepo.findById(productId).orElseThrow(() -> new IllegalArgumentException("no product found!"));
      Wishlist wishlist = user.getWishlist();
      if(wishlist == null ){
         wishlist = new Wishlist();
         user.setWishlist(wishlist);
      }
      System.out.println("if k foran bahir");
      if(product.getPublishedBy()!=userId){
         System.out.println("if k andar");
         wishlist.getProduct().add(product);
         wishlistRepo.save(wishlist);
         userRepo.save(user);
         return ResponseEntity.ok("added to watchlist!");
      }else{
         return (ResponseEntity<?>) ResponseEntity.badRequest();
      }
   }

   public ResponseEntity<?> removeWishlistItem(int userId, int productId){
      User user= userRepo.findById((long)userId).orElseThrow(() -> new IllegalArgumentException("no user found!"));
      Product product = proRepo.findById(productId).orElseThrow(() -> new IllegalArgumentException("no product found!"));
      Wishlist wishlist = user.getWishlist();
      wishlist.getProduct().remove(product);
      wishlistRepo.save(wishlist);
      return ResponseEntity.ok("removed from the list!");  
   }

   public Wishlist getWishlistForUser(int userId){
      User user= userRepo.findById((long)userId).orElseThrow(() -> new IllegalArgumentException("no user found!"));
      Wishlist wishlist = user.getWishlist();
      return wishlist;
   }
    }

