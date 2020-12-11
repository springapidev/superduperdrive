package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @Author Mohammad Rajaul Islam
 * @Since v.1.0
 */
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
