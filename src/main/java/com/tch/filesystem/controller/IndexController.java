package com.tch.filesystem.controller;

import com.tch.filesystem.model.LoginLog;
import com.tch.filesystem.service.LoginLogService;
import com.tch.filesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller()
public class IndexController {

    @Autowired
    private LoginLogService loginLogService;

    @Value("${root.value}")
    private String root;

    @GetMapping("navigation")
    public String navigation(@RequestParam("index") Integer index, HttpSession session){
        String url = root;
        String currRoot = String.valueOf(session.getAttribute("root"));
        String indexs = currRoot.split(url)[1];
        String[] split = indexs.split(File.separator);
        for (int i = 0; i <= index; i++){
            if(i == index){
                url += split[i];
                break;
            }
            url += split[i] + File.separator;
        }
        session.setAttribute("root",url);
        return "redirect:/index";
    }

    @GetMapping("log")
    public String log(HttpSession session, Model model){
        Integer user = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        List<LoginLog>  list = loginLogService.findLogByUid(user);
        model.addAttribute("logList",list);
        return "log";
    }
}
