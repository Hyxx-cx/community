package xyz.yuxx.community.mapper;

import org.apache.ibatis.annotations.*;
import xyz.yuxx.community.model.User;

@Mapper
public interface UserMapper {
    //变量以#{...}的形式，会自动从user中获取到值
    @Insert("insert into USER (account_id,name,token,gmt_create,gmt_modified,avatar_url,login) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatar_url},#{login})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findUserByToken(@Param("token") String token);

    @Select("select * from user where account_id = #{accountId} and login = #{login}")
    User findUserByLoginAndAccountId(User user);

    @Update("update user set token=#{token} where account_id=#{accountId}")
    void updateToken(@Param("token") String token, @Param("accountId") String accountId);

    @Select("select * from user where id = #{creatorId}")
    User findUserById(@Param("creatorId") Integer creatorId);
}
