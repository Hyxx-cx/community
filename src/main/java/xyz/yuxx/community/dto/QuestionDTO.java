package xyz.yuxx.community.dto;

import lombok.Data;
import xyz.yuxx.community.model.User;

/**
 * model是和数据库一一对应的
 * dto，数据传输对象
 * 重在传输
 */

@Data
public class QuestionDTO {

    private Integer id;
    private String  title;
    private String  description;
    private String  tag;
    private Integer creatorId;
    private Integer commentCount;
    private Integer ViewCount;
    private Integer likeCount;
    private Long    gmtCreate;
    private Long    gmtModified;

    private User user;

}
