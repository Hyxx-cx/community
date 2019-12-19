package xyz.yuxx.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xyz.yuxx.community.model.User;

@Mapper
public interface UserMapper {
    //变量以#{...}的形式，会自动从user中获取到值
    @Insert("insert into USER (account_id,name,token,gmt_create,gmt_modified,avatar_url) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatar_url})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findUserByToken(@Param("token") String token);
}
