package xyz.yuxx.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.yuxx.community.dto.PaginationDTO;
import xyz.yuxx.community.dto.QuestionDTO;
import xyz.yuxx.community.mapper.QuestionMapper;
import xyz.yuxx.community.mapper.UserMapper;
import xyz.yuxx.community.model.Question;
import xyz.yuxx.community.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 在QuestionService中不仅可以使用QuestionMapper接口可以使用UserMapper，能够起到一个组装的作用
 * service，业务逻辑层
 */

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;


    public PaginationDTO list(Integer page, Integer size) {
        Integer totalCount = questionMapper.count();
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount, page, size);
        Integer offset = size * (paginationDTO.getPage()-1);
        List<Question> questionList = questionMapper.list(offset,size);        //从数据库取出question数据存于questionList中
        List<QuestionDTO> questionDTOList = new ArrayList<>();      //创建一个questionDTOList用于存放QuestionDTO
        for (Question question : questionList) {                    //遍历questionList
            User user = userMapper.findUserById(question.getCreatorId());   //根据question中的creatorId查找User
            System.out.println(question.getCreatorId());
            if (user != null) {

            System.out.println(user.getAvatarUrl());
            }
            QuestionDTO questionDTO = new QuestionDTO();        //新建一个QuestionDTO用来存放数据

            /**我们可以用最古老的方法将question中的属性一个一个get出来，然后set到DTO中
             * 但是Spring内置了一个简单的转换方法，我们可以直接使用它*/
            BeanUtils.copyProperties(question,questionDTO);//第一个参数是source，第二个参数是target
            //将question数据转换到questionDTO中
            questionDTO.setUser(user);  //将User对象数据存放到questionDTO中
            questionDTOList.add(questionDTO); //向List中存放一个questionDTO
        }
        paginationDTO.setQuestions(questionDTOList);    //将questionDTOList放入到paginationDTO中
        return paginationDTO;
    }
}
