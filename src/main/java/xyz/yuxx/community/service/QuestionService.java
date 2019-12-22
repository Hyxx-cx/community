package xyz.yuxx.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.yuxx.community.dto.QuestionDTO;
import xyz.yuxx.community.mapper.QuestionMapper;
import xyz.yuxx.community.mapper.UserMapper;
import xyz.yuxx.community.model.Question;
import xyz.yuxx.community.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 在QuestionService中不仅可以使用QuestionMapper海口可以使用UserMapper，能够起到一个组装的作用
 * service，业务逻辑层
 */

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;


    public List<QuestionDTO> list() {
        List<Question> questionList = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.findUserById(question.getCreatorId());
            System.out.println(question.getCreatorId());
            if (user != null) {

            System.out.println(user.getAvatarUrl());
            }
            QuestionDTO questionDTO = new QuestionDTO();

            /**我们可以用最古老的方法将question中的属性一个一个get出来，然后set到DTO中
             * 但是Spring内置了一个简单的转换方法，我们可以直接使用它*/
            BeanUtils.copyProperties(question,questionDTO);//第一个参数是source，第二个参数是target

            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
