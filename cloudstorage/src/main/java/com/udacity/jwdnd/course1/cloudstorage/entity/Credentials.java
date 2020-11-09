package com.udacity.jwdnd.course1.cloudstorage.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Credentials {
    private Integer credentialid;
    private String url;
    private String username;
    private String key;
    private String password;
    private String secretkey;
    private int userid;

}
