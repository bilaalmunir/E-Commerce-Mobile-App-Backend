package com.ecommerce.mobileapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.DTO.BilalChampuPayloadDTO;
import com.ecommerce.mobileapp.entities.Comments;
import com.ecommerce.mobileapp.entities.Product;
import com.ecommerce.mobileapp.entities.User;
import com.ecommerce.mobileapp.entities.Wishlist;
import com.ecommerce.mobileapp.repository.CommentRepository;
import com.ecommerce.mobileapp.repository.ProductRepository;
import com.ecommerce.mobileapp.repository.Userrepository;
import com.ecommerce.mobileapp.repository.WishlistRepository;

import java.util.ArrayList;
import java.util.List;

@Service

public class ProductServices {
   @Autowired
   private ProductRepository proRepo;
   @Autowired
   private Userrepository userRepo;
   @Autowired
   private WishlistRepository wishlistRepo;
   @Autowired
   private CommentRepository comRepo;

   public Product addCar(String carName, String model, String color, long price ,int userId) {
      Product Carinfo = new Product(carName, model, color, price , userId);
      proRepo.save(Carinfo);
      return Carinfo;
   }

   public ResponseEntity<String> deleteCar(int carId) {
      Product product = proRepo.findById(carId)
            .orElseThrow(() -> new IllegalArgumentException("no such product found!"));
      List<User> users = userRepo.findAll();
      for (User user : users) {
         Wishlist wishlist = user.getWishlist();
         if (wishlist != null) {
            wishlist.getProduct().removeIf(p -> p.getID() == carId);
            wishlistRepo.save(wishlist);
         }
         List<Product> products = user.getOwnedCars();
         if (products != null) {
            products.removeIf(p -> p.getID() == carId);
            proRepo.saveAll(products);
         }
      }
      proRepo.deleteById(carId);
      return ResponseEntity.ok("Car has been deleted!");
   }

   public Product updateDetails(int userId, int carId, String carName, String model, String color) {
      Product product = proRepo.findById(carId).orElseThrow(() -> new IllegalArgumentException("no product found!"));
      product.setCarName(carName);
      product.setColor(color);
      product.setModel(model);
      proRepo.save(product);
      return product;

   }

   public Product buyCar(int ID, int userID) {
      Product carOptional = proRepo.findById(ID).orElseThrow(() -> new IllegalArgumentException("no product found"));
      User userr = userRepo.findById(userID).orElseThrow(() -> new IllegalArgumentException("no user found!"));
      int carPublishedBy = carOptional.getPublishedBy();
      System.out.println("car Published by Id" + carPublishedBy);
      System.out.println(userID);
      long proPrice = carOptional.getPrice();
      long userBalance = userr.getBalance();
      if (carOptional.getPublishedBy() != userID) {
         if (userBalance >= proPrice) {
            carOptional.setStatus(true);
            carOptional.setBoughtBy(userID);
            long newBalance = userBalance - proPrice;
            userr.setBalance(newBalance);
            List<Product> ownedCars = userr.getOwnedCars();
            ownedCars.add(carOptional);
            System.out.println("saved");
            proRepo.save(carOptional);
            userRepo.save(userr);
            return carOptional;
         }

      }
      return null;
   }

   public List<Product> getAllUnsoldCars() {
      return proRepo.findByStatus(false);
   }

   public List<Product> getAllSoldCars() {
      return proRepo.findByStatus(true);
   }

   public List<Product> getAllCars() {
      return proRepo.findAll();
   }

   public BilalChampuPayloadDTO setWishlistItem(int userId, int productId) {
      User user = userRepo.findById( userId).orElseThrow(() -> new IllegalArgumentException("no user found!"));
      Product product = proRepo.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("no product found!"));
      Wishlist wishlist = user.getWishlist();
      if (wishlist == null) {
         wishlist = new Wishlist();
         user.setWishlist(wishlist);
      }
      System.out.println("if k foran bahir");
      if (product.getPublishedBy() != userId) {
         System.out.println("if k andar");
         wishlist.getProduct().add(product);
         wishlistRepo.save(wishlist);
         userRepo.save(user);
         return new BilalChampuPayloadDTO("addedWatchlist!");
      } else {
         return new BilalChampuPayloadDTO("naihuaHAhahaskhahah");
      }
   }

   public ResponseEntity<?> removeWishlistItem(int userId, int productId) {
      User user = userRepo.findById( userId).orElseThrow(() -> new IllegalArgumentException("no user found!"));
      Product product = proRepo.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("no product found!"));
      Wishlist wishlist = user.getWishlist();
      wishlist.getProduct().remove(product);
      wishlistRepo.save(wishlist);
      return ResponseEntity.ok("removed from the list!");
   }

   public Wishlist getWishlistForUser(int userId) {
      User user = userRepo.findById( userId).orElseThrow(() -> new IllegalArgumentException("no user found!"));
      Wishlist wishlist = user.getWishlist();
      return wishlist;
   }

   public Comments postComment(int productId, int userId, String com) {
      User user = userRepo.findById( userId).orElseThrow(() -> new IllegalArgumentException("no user found!"));
      Product product = proRepo.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("no product found!"));
      List<Comments> comm = product.getComments();
      if (comm == null) {
         comm = new ArrayList<>();
         product.setComments(comm);
      }
      Comments newCom = new Comments();
      newCom.setComment(com);
      newCom.setUser(user);
      comRepo.save(newCom);
      System.out.println(newCom.toString());
      comm.add(newCom);
      product.setComments(comm);
      proRepo.save(product);
      System.out.println(product.toString());
      return newCom;

   }

   public List<Comments> getComments(int productId) {
      Product product = proRepo.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("no product found!"));
      List<Comments> comments = product.getComments();
      if (comments.isEmpty()) {
         System.out.println("comments are empty for this product");
      }

      return comments;
   }
}
