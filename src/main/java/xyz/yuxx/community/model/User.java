package xyz.yuxx.community.model;

import lombok.Data;

/**
 * model这边是数据库模型，是和数据库一一对应的
 * 数据库有的要有，数据库没的不要
 */
@Data
public class User {
    /**
     * 为了能够将基本数据类型当成对象操作，Java为每一个基本数据类型都引入了
     * 对应的包装类型（wrapper class），int的包装类就是Integer
     */

    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
    private String login;

}
