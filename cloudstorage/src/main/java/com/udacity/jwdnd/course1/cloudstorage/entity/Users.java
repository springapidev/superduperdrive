package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class Users {
    private int userid;;
    private String userName;
    private String salt;
    private String password;
    private String firstName;
    private String lastName;
    private List<UserRole> roles;

  }
