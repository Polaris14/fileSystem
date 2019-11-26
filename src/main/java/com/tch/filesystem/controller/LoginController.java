package com.tch.filesystem.controller;

import com.tch.filesystem.model.LoginLog;
import com.tch.filesystem.model.User;
import com.tch.filesystem.service.LoginLogService;
import com.tch.filesystem.service.UserService;
import com.tch.filesystem.tools.CategoryTool;
import com.tch.filesystem.tools.UrlSplitTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Controller
public class LoginController {

    @Value("${root.value}")
    private String path;

    @Autowired
    private CategoryTool categoryTool;

    @Autowired
    private UrlSplitTool urlSplitTool;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginLogService loginLogService;

    @GetMapping("index")
    public String index(Model model, HttpSession session){
        //获取当前路径
        String root = String.valueOf(session.getAttribute("root"));
        try{
            //获取当前路径下的文件
            File file = new File(root);
            String[] list = file.list();
            HashSet<String> video = new HashSet<>();
            HashSet<String> image = new HashSet<>();
            HashSet<String> document = new HashSet<>();
            HashSet<String> folder = new HashSet<>();
            HashSet<String> music = new HashSet<>();
            if(list != null || list.length != 0){
                for (int i = 0; i < list.length; i++){
                    String[] fileName = list[i].split("\\.");
                    if(fileName.length == 1){
                        folder.add(list[i]);
                    }
                    if("video".equals(categoryTool.cate(fileName[fileName.length-1]))){
                        video.add(list[i]);
                    }
                    if("image".equals(categoryTool.cate(fileName[fileName.length-1]))){
                        image.add(list[i]);
                    }
                    if("document".equals(categoryTool.cate(fileName[fileName.length-1]))){
                        document.add(list[i]);
                    }
                    if("music".equals(categoryTool.cate(fileName[fileName.length-1]))){
                        music.add(list[i]);
                    }
                }
            }
            //获取导航
            List<String> navigation = urlSplitTool.split(root);

            model.addAttribute("videos",video);
            model.addAttribute("images",image);
            model.addAttribute("documents",document);
            model.addAttribute("folders",folder);
            model.addAttribute("musics",music);
            model.addAttribute("navigations",navigation);
        }catch (NullPointerException e){
            return "error";
        }
        return "index";
    }

    @PostMapping("login")
    @ResponseBody
    public Map login(@RequestBody User user,HttpSession session){
        HashMap<String, String> map = new HashMap<>();
        map.put("code","0");
        User u = userService.userFindByPhoneAndPassword(user);
        if(u != null){
            //添加登录日志
            try {
                String address = InetAddress.getLocalHost().getHostAddress();
                long date = new Date().getTime();
                LoginLog log = new LoginLog();
                log.setLip(address);
                log.setLtime(date);
                log.setUid(u.getUid());
                loginLogService.insertLog(log);
                map.put("code","1");
                //设置当前路径
                session.setAttribute("root",path + u.getPhone());
                //设置当前用户
                session.setAttribute("user",u.getPhone());
                session.setAttribute("userId",u.getUid());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return map;
        }
        return map;
    }

    @PostMapping("register")
    @ResponseBody
    public Map register(@RequestBody User user){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("code","0");
        String filepath = path + user.getPhone();
        File file = new File(filepath);
        if(!file.exists()){
            //创建文件夹
            file.mkdir();
            //注册用户
            userService.userRegister(user);
            hashMap.put("code","1");
            return hashMap;
        }
        return hashMap;
    }

    @GetMapping("register")
    public String register(){
        return "register";
    }

    @GetMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/index";
    }
}

