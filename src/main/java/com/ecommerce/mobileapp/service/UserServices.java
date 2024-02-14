package com.ecommerce.mobileapp.service;

import com.ecommerce.mobileapp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.mobileapp.repository.Userrepository;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class UserServices {
    @Autowired
    private Userrepository userRepo;

        public User registerUser(String userName, String firstname, String lastname, String email, String password, byte[] profilePicture){
            User user = new User();
         if (userRepo.findByUsername(userName)!=null) {
            return new User();
         }
        user.setUsername(userName);
       user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setPassword(encryptThisString(password));
        String base64EncodedProfilePicture = Base64.getEncoder().encodeToString(profilePicture);
    user.setProfilePicture(base64EncodedProfilePicture);
        
        // user.setPassword(password);
        return userRepo.save(user);
        }


         public static String encryptThisString(String input) {
        try {
            MessageDigest md =MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
        public User loginUser(String userName,String password){
            User user = userRepo.findByUsername(userName);
            if(user!= null){
                String check = encryptThisString(password);
                if (user.getPassword().equals(check)){
                    System.out.println("hogya" );
                    return user;
                }
            }
            throw new IllegalArgumentException("mango mango");
        }

        public User getUser(int userId){
            return userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("no user found!"));

        }


}
