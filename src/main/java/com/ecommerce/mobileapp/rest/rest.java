package com.ecommerce.mobileapp.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.DTO.BilalChampuPayloadDTO;
import com.ecommerce.mobileapp.entities.Comments;
import com.ecommerce.mobileapp.entities.Product;
import com.ecommerce.mobileapp.entities.User;
import com.ecommerce.mobileapp.entities.Wishlist;
import com.ecommerce.mobileapp.service.ProductServices;
import com.ecommerce.mobileapp.service.UserServices;

@org.springframework.stereotype.Controller


@RestController    
public class rest {
    @Autowired
    private ProductServices ps ;
    @Autowired
    private UserServices us;
    

    @PostMapping("/registerUser")
    public User registerUser(@RequestParam String username, @RequestParam String firstname, @RequestParam String lastname,
            @RequestParam String email, @RequestParam String password, @RequestParam byte[] profilePicture) {
        return us.registerUser(username, firstname, lastname, email, password, profilePicture);
    }
    @PostMapping("/loginUser")
    public User loginUser(@RequestParam String username,@RequestParam String password){
        return us.loginUser(username, password);
    }
    
    @PostMapping("/addCar")
    public ResponseEntity<?> addCar(@RequestParam String carName,@RequestParam String model,@RequestParam String color, @RequestParam Long price,@RequestParam int userId){
        Product addedCar = ps.addCar(carName, model, color, price ,userId);
        return ResponseEntity.ok(addedCar);
    }

    @DeleteMapping("/deleteCar")
    public ResponseEntity<String> deleteCar(@RequestParam int carId){
        return ps.deleteCar(carId);
    }

    @PutMapping("/updateDetails")
    public Product updateDetails(int userId,int carId, String carName,String model,String color ){
        return ps.updateDetails(userId, carId, carName, model, color);
    }
    
    @PutMapping("/buyCar")
    public Product buyCar (@RequestParam int ID, @RequestParam int userID){
        return ps.buyCar(ID,userID);
    }
    @GetMapping("/getAllUnsoldCars")
    public List<Product> getAllUnsoldCars(){
        return ps.getAllUnsoldCars();
    }

    @GetMapping("/getAllSoldCars")
    public List<Product> getAllSoldCars(){
        return ps.getAllSoldCars();
    }
    
    @GetMapping("/getAllCars")
    public List<Product> getAllCars(){
        return ps.getAllCars();
    }

    @PostMapping("/setWishlistItem")
    public Product setWishlist(int userId, int productId){
        return ps.setWishlistItem(userId, productId);
    }

    @PutMapping("/removeWishlistItem")
    public ResponseEntity<String> removeWishlistItem(int userId, int productId){
        return ps.removeWishlistItem(userId, productId);
    }

    @GetMapping("/getWishlistForUser")
   public Wishlist getWishlistForUser(int userId){
        return ps.getWishlistForUser(userId);
    }
    @PostMapping("/postComment")
    public Comments postComment(int productId, int userId, String com){
        return ps.postComment(productId, userId, com);
    }
    @GetMapping("/getComments")
    public List<Comments> getComments(int productId){
        return ps.getComments(productId);
    }
    @GetMapping("/getUser")
    public User getUser(int userId){
        return us.getUser(userId);
    }

}

