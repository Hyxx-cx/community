package xyz.yuxx.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
    /**
     * @return
     * @RequestParam：绑定请求中的参数； 自SpringMVC4.2之后，RequestParam内部多了一个name参数
     * name和value是等价的，都是指定前台的参数名。二者只能存在一个，不能同时使用。
     * required：该参数是否为必传项。默认是true，表示请求中一定要传入对应的参数
     */
    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "world") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

}
