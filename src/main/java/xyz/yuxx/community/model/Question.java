package xyz.yuxx.community.model;

import lombok.Data;

@Data
public class Question {
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


}
