package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsersMapper {
    @Select("select * from users")
    public List<Users> findAll();

    @Select("SELECT * FROM users WHERE userid = #{userid}")
    public Users findById(long id);
    @Select("SELECT * FROM users WHERE username = #{username}")
    public Users findByUserName(String username);
    @Delete("DELETE FROM users WHERE userid = #{userid}")
    public int deleteById(long id);

    @Insert("INSERT INTO users(userid, username , salt, password, firstname, lastname) " +
            " VALUES (#{userid}, #{userName}, #{salt}, #{password}, #{firstName}, #{lastName})")
    public int insert(Users users);

    @Update("Update users set firstname=#{firstName}, " +
            " lastname=#{lastName}, password=#{password} where userid=#{userid}")
    public int update(Users users);
}
