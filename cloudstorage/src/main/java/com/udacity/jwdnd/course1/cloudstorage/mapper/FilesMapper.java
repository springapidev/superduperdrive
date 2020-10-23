package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Files;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper
public interface FilesMapper {

    @Select("select * from files")
    public List<Files> findAll();

    @Select("select * from files where userid = #{userid}")
    public List<Files> findAllByUsername(int userid);

    @Select("SELECT * FROM files WHERE fileId = #{fileId}")
    public Files findById(long id);
    @Select("SELECT * FROM files WHERE filename = #{filename}")
    public Files findByFilename(String filename);
    @Delete("DELETE FROM files WHERE fileId = #{fileId}")
    public int deleteById(long id);

    @Insert("INSERT INTO files(fileId, filename , contenttype, userid, filedata) " +
            " VALUES (#{fileId}, #{filename}, #{contenttype}, #{userid}, #{filedata})")
    public int insert(Files files) throws IOException;

 }
