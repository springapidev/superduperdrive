package com.udacity.jwdnd.course1.cloudstorage.entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notes {
    private int noteid;
    private String notetitle;
    private String notedescription;
    private int userid;
}
