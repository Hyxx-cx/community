package xyz.yuxx.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.yuxx.community.model.User;
import xyz.yuxx.community.dto.AccessTokenDTO;
import xyz.yuxx.community.dto.GitHubUser;
import xyz.yuxx.community.mapper.UserMapper;
import xyz.yuxx.community.provider.GitHubProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;


@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;
    @Autowired
    private UserMapper userMapper;

    @Value("${github.redirect.uri}")
    private String redirect_uri;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.client.id}")
    private String client_id;

    @GetMapping("/callback")
    public String callback(@RequestParam(value = "code") String code,
                           @RequestParam(value = "state") String state,
                           HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();  //获取session
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setState(state);
        String access_token = gitHubProvider.getAccessToken(accessTokenDTO);//获取access_token
        GitHubUser gitHubUser = gitHubProvider.getGitHubUser(access_token);//获取user信息
        if (gitHubUser != null) {
            User user = new User();
            user.setAccountId(String.valueOf(gitHubUser.getId()));//获取GitHub账号的id，并将id的Long类型转换成String类型
            user.setName(gitHubUser.getName()); //获取账号昵称
            user.setToken(UUID.randomUUID().toString());   //生成一个UUID
            user.setGmtCreate(System.currentTimeMillis());  //毫秒计数格林威治时间
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);    //插入数据库
            session.setAttribute("gitHubUser", gitHubUser);
            //return "redirect:/index.html";//error
            return "redirect:/";// 以重定向的方式返回到index页面，地址会显示index地址
        } else {
            return "redirect:/";
        }

        //return "index"; //以转发的方式返回到index页面，地址栏会显示之前的地址
    }
}
