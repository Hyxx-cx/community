package xyz.yuxx.community.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import xyz.yuxx.community.dto.AccessTokenDTO;
import xyz.yuxx.community.dto.GitHubUser;

import java.io.IOException;

@Component
public class GitHubProvider {

    //post请求
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        /**
         * 使用okhttp发送一个post请求，请求的参数存放在accessTokenDTO这个实体类中
         * 通过toJSONString将实体类转换成json样式的字符串。json就是类似map那种
         * json样式字符串就长这样{"a": "Hello", "b": "World"}，键值一一对应
         */
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO) );
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();//就是获得响应的字符串类型数据
            String access_token = string.split("=")[1].split("&")[0];
            //根据拿到的原始accesstoken字符串分裂得到我们要的access_token
            System.out.println(access_token);
            return access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    //get请求
    public GitHubUser getGitHubUser(String access_token)  {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + access_token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();//获取到json类型的数据
            System.out.println(string);
            GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);//将json数据保存到实体类中
            return  gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
