package xyz.yuxx.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.yuxx.community.mapper.UserMapper;
import xyz.yuxx.community.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String Index(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        //如果我们浏览器中没有cookie那么cookies将为空，因此如果不做if判断会出现空指针异常
        //首次登录自然是没有cookie的
        if(cookies != null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findUserByToken(token);
                    if(user != null){
                        httpServletRequest.getSession().setAttribute("gitHubUser",user);
                    }
                    break;
                }
            }
        }


        return "index";
    }
}
