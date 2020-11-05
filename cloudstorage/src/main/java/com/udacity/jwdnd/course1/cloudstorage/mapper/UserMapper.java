package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from users")
    public List<User> findAll();

    @Select("SELECT * FROM users WHERE userid = #{userid}")
    public User findById(long id);
    @Select("SELECT * FROM users WHERE username = #{username}")
    public User findByUserName(String username);
    @Delete("DELETE FROM users WHERE userid = #{userid}")
    public int deleteById(long id);

    @Insert("INSERT INTO users(userid, username , salt, password, firstname, lastname) " +
            " VALUES (#{userid}, #{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    public int insert(User users);

    @Update("Update users set firstname=#{firstName}, " +
            " lastname=#{lastName}, password=#{password} where userid=#{userid}")
    public int update(User users);
}
