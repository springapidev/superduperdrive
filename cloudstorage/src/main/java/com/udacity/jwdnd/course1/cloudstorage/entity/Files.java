package com.udacity.jwdnd.course1.cloudstorage.entity;

import java.sql.Blob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Files {
    private int fileId;
    private String filename;
    private String contenttype;
    private int userid;
    private byte[] filedata;
}
