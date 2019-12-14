package xyz.yuxx.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.yuxx.community.dto.AccessTokenDTO;
import xyz.yuxx.community.dto.GitHubUser;
import xyz.yuxx.community.provider.GitHubProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

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
            session.setAttribute("gitHubUser", gitHubUser);
            //return "redirect:/index.html";//error
            return "redirect:/";// 以重定向的方式返回到index页面，地址会显示index地址
        } else {
            return "redirect:/";
        }

        //return "index"; //以转发的方式返回到index页面，地址栏会显示之前的地址
    }
}
