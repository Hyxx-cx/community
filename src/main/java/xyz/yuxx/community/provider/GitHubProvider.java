package xyz.yuxx.community.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import xyz.yuxx.community.dto.AccessTokenDTO;
import xyz.yuxx.community.dto.GitHubUser;

import java.io.IOException;

@Component
public class GitHubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();


            RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO) );
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String access_token = string.split("=")[1].split("&")[0];
                System.out.println(access_token);
                return access_token;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

    }

    public GitHubUser getGitHubUser(String access_token)  {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + access_token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println(string);
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
            return  gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
