package xyz.yuxx.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import xyz.yuxx.community.model.Question;

@Mapper
public interface QuestionMapper {

    //将question中的数据插入到数据库中.
    @Insert("insert into question (title,description,tag,creatorId,gmt_Create,gmt_Modified) values (#{title},#{description},#{tag},#{creatorId},#{gmtCreate},#{gmtModified})")
    void create(Question question);
}
