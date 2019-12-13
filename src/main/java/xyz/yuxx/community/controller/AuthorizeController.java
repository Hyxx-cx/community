package xyz.yuxx.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.yuxx.community.dto.AccessTokenDTO;
import xyz.yuxx.community.dto.GitHubUser;
import xyz.yuxx.community.provider.GitHubProvider;


@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;


    @GetMapping("/callback")
    public String callback(@RequestParam(value = "code") String code,
                           @RequestParam(value = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setClient_id("73b35e5a3e56e148322c");
        accessTokenDTO.setClient_secret("cd62b18b7044c8aefebe67f5c8e428a62418fb16");
        accessTokenDTO.setState(state);
        String access_token = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getGitHubUser(access_token);
        System.out.println(gitHubUser.getId());
        System.out.println(gitHubUser.getName());
        System.out.println(gitHubUser.getBio());

        return "index"; //返回到index页面
    }
}
