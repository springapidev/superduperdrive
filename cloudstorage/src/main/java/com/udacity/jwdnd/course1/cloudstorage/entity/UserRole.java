package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.Getter;
import lombok.Setter;


public class UserRole {
     private String role;

     public UserRole() {
          this.role = "ROLE_USER";
     }

     public String getRole() {
          return role;
     }

     public void setRole(String role) {
          this.role = role;
     }
}
