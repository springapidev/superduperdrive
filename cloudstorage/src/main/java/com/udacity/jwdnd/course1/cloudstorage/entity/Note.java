package com.udacity.jwdnd.course1.cloudstorage.entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Note {
    private int noteid;
    private String notetitle;
    private String notedescription;
    private int userid;
}
