package com.udacity.jwdnd.course1.cloudstorage.entity;

import java.sql.Blob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Credentials {
    private Integer credentialid;
    private String url;
    private String username;
    private String key;
    private String password;
    private String secretkey;
    private int userid;

}
