package com.udacity.jwdnd.course1.cloudstorage.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
/**
 * @Author Mohammad Rajaul Islam *
 * @Since v.1.0
 */
@Getter
@Setter
public class FileDto {
    private int fileId;
    private String filename;
    private String contenttype;
    private int userid;
    private String photo;

    public FileDto(int fileId, String filename, String contenttype, int userid, String filedata) {
        this.fileId = fileId;
        this.filename = filename;
        this.contenttype = contenttype;
        this.userid = userid;
        this.photo = filedata;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
