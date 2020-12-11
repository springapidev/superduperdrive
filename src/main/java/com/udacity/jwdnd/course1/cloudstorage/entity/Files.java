package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.Getter;
import lombok.Setter;
/**
 * @Author Mohammad Rajaul Islam *
 * @Since v.1.0
 */
@Getter
@Setter
public class Files {
    private int fileId;
    private String filename;
    private String contenttype;
    private int userid;
    private byte[] filedata;
}
