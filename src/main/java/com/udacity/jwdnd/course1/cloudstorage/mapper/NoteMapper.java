package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;
/**
 * @Author Mohammad Rajaul Islam
 * @Since v.1.0
 */
@Mapper
public interface NoteMapper {
    @Select("select * from notes")
    public List<Note> findAll();
    @Select("select * from notes where userid = #{userid}")
    public List<Note> findAllByUsername(int userid);

    @Select("SELECT * FROM notes WHERE noteid = #{noteid}")
    public Note findById(long id);
    @Select("SELECT * FROM notes WHERE notetitle = #{notetitle}")
    public Note findByNotetitle(String notetitle);
    @Delete("DELETE FROM notes WHERE noteid = #{noteid}")
    public int deleteById(long id);

    @Insert("INSERT INTO notes(noteid, notetitle , notedescription, userid) " +
            " VALUES (#{noteid}, #{notetitle}, #{notedescription}, #{userid})")
    public int insert(Note notes);

    @Update("Update notes set notetitle=#{notetitle}, " +
            " notedescription=#{notedescription} where noteid=#{noteid}")
    public int update(Note notes);
}
