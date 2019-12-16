## community


## 资料
[Spring 文档](https://spring.io/guides)    
[Spring Web](https://spring.io/guides/gs/serving-web-content/)    
[elasticsearch](https://elasticsearch.cn/)    
[GitHub deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)    
[Bootstrap中文网](https://www.bootcss.com/)  
[Bootstrap菜鸟教程](https://www.runoob.com/bootstrap/bootstrap-tutorial.html)
[flyway](https://flywaydb.org/getstarted/firststeps/maven)  


## 工具
[Git](https://git-scm.com/)   
[visual-paradigm](https://www.visual-paradigm.com/tw/)  
[H2 Database Engine](http://www.h2database.com/html/main.html)  


## 命令
mvn flyway:migrate

## 脚本
```sql
-- Create_user_table
create table user
(
   id int auto_increment,
   account_id varchar(100),
   name varchar(50),
   token char(36),
   gmt_create bigint,
   gmt_modified bigint,
   constraint user_pk
        primary key (id)
);
```

```sql
-- Add_bio_col
alter table USER
    add bio varchar(256);
```

