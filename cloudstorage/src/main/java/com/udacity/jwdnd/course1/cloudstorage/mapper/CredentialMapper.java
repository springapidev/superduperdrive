package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("select * from credentials")
    public List<Credentials> findAll();

    @Select("select * from credentials where userid = #{userid}")
    public List<Credentials> findAllByUsername(int userid);

    @Select("SELECT * FROM credentials WHERE credentialid = #{credentialid}")
    public Credentials findById(long id);

    @Select("SELECT * FROM credentials WHERE url = #{url}")
    public Credentials findByUrl(String url);

    @Delete("DELETE FROM credentials WHERE credentialid = #{credentialid}")
    public int deleteById(long credentialid);

    @Insert("INSERT INTO credentials(credentialid,url, username , key,password, secretkey, userid) " +
            " VALUES (#{credentialid}, #{url}, #{username}, #{key},#{password},#{secretkey}, #{userid})")
    public int insert(Credentials credentials);

    @Update("Update credentials set url=#{url}, username=#{username},key=#{key},secretkey=#{secretkey}," +
            " password=#{password} where credentialid=#{credentialid}")
    public int update(Credentials credentials);
}
