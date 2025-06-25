package com.example.desmosecommerce.dao;

import com.example.desmosecommerce.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(String email);
    
    @Insert("INSERT INTO users (fullname, email, password, is_admin) VALUES (#{fullname}, #{email}, #{password}, #{isAdmin})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);
    
    @Select("SELECT * FROM users WHERE email = #{email} AND password = #{password} LIMIT 1")
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
} 