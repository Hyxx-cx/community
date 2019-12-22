package xyz.yuxx.community.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.yuxx.community.model.Question;

import java.util.List;

@Mapper
public interface QuestionMapper {

    //从数据库读出来的数据会一一对应放入list中
    @Select("select * from question")
    List<Question> list();

    //将question中的数据插入到数据库中.
    @Insert("insert into question (title,description,tag,creatorId,gmt_Create,gmt_Modified) values (#{title},#{description},#{tag},#{creatorId},#{gmtCreate},#{gmtModified})")
    void create(Question question);

}
