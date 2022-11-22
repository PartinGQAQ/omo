package com.itheima.dao;

import com.itheima.model.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO t_user(username,password,email,created,valid,level,ex_point)" +
    "values (#{username},#{password},#{email},#{created},#{valid},#{level},#{ex_point})")
    public void insertUser(User user);

    @Select("Select * From t_user Where id = #{id}")
    public User findById(Integer id);

    @Select("Select * From t_user")
    public List<User> findAllUser();

    @Select("Select * From t_user where username=#{username}")
    public User findByUsername(String username);

    @Select("Select * From t_user Where email=#{email}")
    public User findByEmail(String email);

    @Insert("insert INTO t_user_authority(user_id,authority_id)" +
            "values (#{user_id},#{authority_id})")
    public void insertAuthority(Integer user_id,Integer authority_id);
}
