package com.udacity.jwdnd.course1.cloudstorage.dto;

import lombok.Getter;
import lombok.Setter;
/**
 * @Author Mohammad Rajaul Islam *
 * @Since v.1.0
 */
@Getter
@Setter
public class CredentialsDto {
    private String decryptedpassword;
    private String secretkey;
    public CredentialsDto() {
    }
}
