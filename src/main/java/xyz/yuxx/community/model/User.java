package xyz.yuxx.community.model;

import lombok.Data;

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
    private String avatar_url;


}
