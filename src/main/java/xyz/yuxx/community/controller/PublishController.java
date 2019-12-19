package xyz.yuxx.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.yuxx.community.mapper.QuestionMapper;
import xyz.yuxx.community.mapper.UserMapper;
import xyz.yuxx.community.model.Question;
import xyz.yuxx.community.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String Publish(){
        return "publish";
    }


    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title") String title,
                            @RequestParam(value = "description") String description,
                            @RequestParam(value = "tag") String tag,
                            HttpServletRequest request,
                            Model model){
        //模型对象(model)的作用主要是保存数据，可以借助它们将数据带到前端
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        //用于判断标题 内容 标签 是否为空,为空报出错误提示信息
        if(title == null || title == ""){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if(description == null || description == ""){
            model.addAttribute("error", "内容不能为空");
            return "publish";
        }
        if(tag == null || tag == ""){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        //根据cookie中的token获取到用户信息
        Cookie[] cookies = request.getCookies();
        User user = null;
        if(cookies != null && cookies.length != 0 ){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.findUserByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("User",user);
                    }
                    break;
                }
            }
        }else {
            model.addAttribute("error", "cookie为空");
        }

        /**
         * 会有bug，因为我们是拿cookie中的token去换user信息
         * 我们第一次登录会生成cookie但不会再次执行此方法去获取用户信息
         * 但是，我们登录完会先跳转到indexController去，我们再进入publishController就是有cookie的
         * 所以应该也能正常运行
         */

        //如果user对象为空,说明还没有用户信息,用户未登录
        if(user == null){
            model.addAttribute("error", "用户未登录");
            return "publish";

        }

        //将表单以post请求提交的数据存放到question实体类中
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreatorId(user.getId());
        question.setGmtCreate(System.currentTimeMillis());//当前时间
        question.setGmtModified(question.getGmtCreate());
        //将question实体类中的数据写入到H2数据库中
        questionMapper.create(question);
        return "redirect:/";
    }
}


