
package com.ecommerce.mobileapp.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class Comments {
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private long ID;
        private String Comment;
        @ManyToOne
        private User user;

        public long getID() {
            return ID;
        }
        public String getComment() {
            return Comment;
        }
        @Override
        public String toString() {
            return "Comments [ID=" + ID + ", Comment=" + Comment + ", user=" + user + "]";
        }
        public void setComment(String comment) {
            Comment = comment;
        }
        public User getUser() {
            return user;
        }
        public void setUser(User user) {
            this.user = user;
        }

}