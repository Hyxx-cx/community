package xyz.yuxx.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import xyz.yuxx.community.model.User;

@Mapper
public interface UserMapper {
    //变量以#{...}的形式，会自动从user中获取到值
    @Insert("insert into USER (account_id,name,token,gmt_create,gmt_modified) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
