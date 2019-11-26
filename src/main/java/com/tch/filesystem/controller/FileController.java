package com.tch.filesystem.controller;

import com.tch.filesystem.tools.DeleteFile;
import com.tch.filesystem.tools.GetDataTool;
import com.tch.filesystem.tools.ZipTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;
import java.util.zip.ZipOutputStream;

@Controller
public class FileController {

    @Autowired
    private DeleteFile deleteFile;

    @Autowired
    private ZipTool zipTool;

    @Autowired
    private GetDataTool getDataTool;

    @Value("${root.value}")
    private String root;

    @Value("${root.nginx}")
    private String nginxPath;

    @GetMapping("/file/new")
    public String newPage(){
        return "new";
    }

    @GetMapping("file/createFolder")
    @ResponseBody
    public Map createFolder(@RequestParam("folderName") String folderName, HttpSession session){
        HashMap<String, String> map = new HashMap<>();
        map.put("code","1");
        String root = String.valueOf(session.getAttribute("root"));
        File file = new File(root + File.separator + folderName);
        if(file.exists()){
            map.put("code","0");
            return map;
        }
        file.mkdir();
        return map;
    }

    @GetMapping("file/next")
    public String folder(@RequestParam("folder") String folderName, HttpSession session){
        String root = String.valueOf(session.getAttribute("root"));
        session.setAttribute("root",root + File.separator  + folderName);
        return "redirect:/index";
    }

    @GetMapping("file/newName")
    public String newName(@RequestParam("name") String name, Model model){
        model.addAttribute("oldName",name);
        return "newname";
    }

    @GetMapping("file/updateName")
    @ResponseBody
    public Map updateName(@RequestParam("name") String name, @RequestParam("oldName") String oldName,HttpSession session){
        HashMap<String, String> map = new HashMap<>();
        map.put("code","1");
        String root = String.valueOf(session.getAttribute("root"));
        String[] split = oldName.split("\\.");
        if(split.length != 1){
            String kz = split[1];
            File file = new File(root + File.separator  + name + "." + kz);
            if(file.exists()){
                map.put("code","0");
                return map;
            }
            File old = new File(root + File.separator  + oldName);
            old.renameTo(new File(root + File.separator  + name + "." + kz));
            return map;
        }
        File file = new File(root + File.separator  + name);
        if(file.exists()){
            map.put("code","0");
            return map;
        }
        File old = new File(root + File.separator  + oldName);
        old.renameTo(new File(root + File.separator  + name));
        return map;
    }

    @GetMapping("/file/remove")
    public String remove(@RequestParam("name") String name,HttpSession session){
        String root = String.valueOf(session.getAttribute("root"));
        String path = root + File.separator + name;
        File file = new File(path);
        deleteFile.deleteFile(file);
        return "redirect:/index";
    }

    @GetMapping("/file/download")
    public void upload(@RequestParam("name") String name, HttpSession session, HttpServletResponse response){
        try{
            String url = session.getAttribute("root") + File.separator + name;
            File file = new File(url);
            //告知浏览器下载文件，而不是直接打开，浏览器默认为打开
            response.setContentType("application/x-download");
            if(file.isDirectory()){
                ZipOutputStream stream = new ZipOutputStream(new FileOutputStream(root + File.separator + name + ".zip"));
                zipTool.zip(stream,file,name);
                stream.close();
                InputStream inStream = new FileInputStream(root + File.separator + name + ".zip");

                response.setHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes("utf-8"),"ISO8859-1") +".zip");
                // 循环取出流中的数据
                download(response, inStream);
                File file1 = new File(root + File.separator + name + ".zip");
                file1.delete();
            }else{
                //下载文件的默认名称
                response.setHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes("utf-8"),"ISO8859-1"));
                InputStream inStream = new FileInputStream(file);
                // 循环取出流中的数据
                download(response, inStream);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //下载
    private void download(HttpServletResponse response, InputStream inStream) throws IOException {
        byte[] b = new byte[1024];
        int len;
        while ((len = inStream.read(b)) > 0){
            response.getOutputStream().write(b, 0, len);
        }
        inStream.close();
        response.getOutputStream().close();
    }

    @GetMapping("/file/move")
    public String move(@RequestParam("name") String name,HttpSession session){
        session.setAttribute("name",name);
        return "move";
    }

    @GetMapping("/file/getData")
    @ResponseBody
    public Set getData(HttpSession session){
        String besides = session.getAttribute("root") + File.separator +session.getAttribute("name");
        String user = String.valueOf(session.getAttribute("user"));
        Set data = getDataTool.getData(root + user, besides);
        return data;
    }

    @PostMapping("/file/renameTo")
    @ResponseBody
    public Map renameTo(@RequestBody String urlObj,HttpSession session) throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<>();
        String curr = String.valueOf(session.getAttribute("root"));
        String newName = session.getAttribute("name") + "";
        String currUrl = curr + File.separator + session.getAttribute("name");
        File newFile = new File(urlObj + File.separator + newName);
        if(newFile.exists()){
            map.put("code","0");
            return map;
        }
        File currFile = new File(currUrl);
        currFile.renameTo(newFile);
        map.put("code","1");
        return map;
    }

    @PostMapping("file/upload")
    @ResponseBody
    public Map upload(MultipartFile file,HttpSession session){
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, String> ChildrenMap = new HashMap<>();
        try {
            map.put("code","1");
            map.put("msg","");
            ChildrenMap.put("src","");
            map.put("data",ChildrenMap);
            String filename = file.getOriginalFilename();
            String currPath = String.valueOf(session.getAttribute("root"));
            File toFile = new File(currPath + File.separator + filename);
            if(toFile.exists()){
                map.put("code","0");
                return map;
            }
            file.transferTo(toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @GetMapping("/file/video")
    public String video(@RequestParam("name") String name,Model model,HttpSession session){
        try{
            String currPath = session.getAttribute("root") + File.separator +name;
            String src = nginxPath + currPath.split(root)[1];
            model.addAttribute("src",src);
            if("mp3".equals(name.split("\\.")[1])){
                return "music";
            }
            if("jpg".equals(name.split("\\.")[1]) || "png".equals(name.split("\\.")[1])){
                return "images";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "video";
    }

    @GetMapping("/file/text")
    public String text(@RequestParam("name") String name,Model model,HttpSession session){
        try{
            String currPath = session.getAttribute("root") + File.separator +name;
            FileInputStream stream = new FileInputStream(currPath);
            InputStreamReader reader = new InputStreamReader(stream,"gbk");
            BufferedReader br = new BufferedReader(reader);
            String line;
            String data = "";
            while ((line = br.readLine()) != null){
                line += "</br>";
                data += line;
            }
            br.close();
            reader.close();
            stream.close();
            System.out.println(data);
            model.addAttribute("data",data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "text";
    }
}
