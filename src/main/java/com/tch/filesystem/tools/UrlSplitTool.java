package com.tch.filesystem.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class UrlSplitTool {

    @Value("${root.value}")
    private String root;

    public List split(String url){
        String attr = url.split(root)[1];
        String[] path = attr.split(File.separator);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < path.length; i++){
            if(i == 0){
                list.add("根目录");
                continue;
            }
            list.add(path[i]);
        }
        return list;
    }
}
