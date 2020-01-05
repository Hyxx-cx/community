package xyz.yuxx.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.yuxx.community.dto.PaginationDTO;
import xyz.yuxx.community.dto.QuestionDTO;
import xyz.yuxx.community.mapper.QuestionMapper;
import xyz.yuxx.community.mapper.UserMapper;
import xyz.yuxx.community.model.Question;
import xyz.yuxx.community.model.User;
import xyz.yuxx.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {

    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private QuestionService questionService;

    @Value("${github.index.url}")
    private String indexUrl;

    @GetMapping("/")
    public String Index(HttpServletRequest httpServletRequest,
                        Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page, //接收page和size参数，决定显示第几页，每页显示多少
                        @RequestParam(value = "size",defaultValue = "5") Integer size){
        model.addAttribute("indexUrl", indexUrl);
        Cookie[] cookies = httpServletRequest.getCookies();
        //如果我们浏览器中没有cookie那么cookies将为空，因此如果不做if判断会出现空指针异常
        //首次登录自然是没有cookie的

        if(cookies != null && cookies.length != 0){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findUserByToken(token);
                    if(user != null){
                        httpServletRequest.getSession().setAttribute("User",user);
                        /** 将用户信息放到session中，便于html调用*/
                    }
                    break;
                }
            }
        }
        /**
         * 我们在页面显示的应该还包括用户头像
         * 如果仅仅是question这个表的信息是不够用的，question这个model也就不够用
         * 我们还要创建一个questionDTO，这个dto包含了question的全部信息，同时还包含发布该question的用户信息对象
         * 因此最终在页面展示时，我们要使用questionDTO这里边的数据
         * QuestionDTO怎么来？
         * 我们定义一个questionService，在该类内对question和user进行组装生成QuestionDTO
         */
        PaginationDTO pagination = questionService.list(page,size);       //需要将page和size参数传到list方法中，决定每一页取出来的questions

//        System.out.println(questionList);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
