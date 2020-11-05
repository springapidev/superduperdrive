package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class User {
    private int userid;
    private String username;
    private String salt;
    private String password;
    private String firstName;
    private String lastName;

  }
