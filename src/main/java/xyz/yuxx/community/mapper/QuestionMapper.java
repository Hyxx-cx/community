package xyz.yuxx.community.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xyz.yuxx.community.model.Question;

import java.util.List;

@Mapper
public interface QuestionMapper {

    //从数据库读出来的数据会一一对应放入list中
    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    //将question中的数据插入到数据库中.
    @Insert("insert into question (title,description,tag,creatorId,gmt_Create,gmt_Modified) values (#{title},#{description},#{tag},#{creatorId},#{gmtCreate},#{gmtModified})")
    void create(Question question);

    @Select("select count(1) from question")
    Integer count();
}
