package com.udacity.jwdnd.course1.cloudstorage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsDto {
    private String decryptedpassword;
    private String secretkey;
    public CredentialsDto() {
    }
}
