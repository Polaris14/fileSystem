package com.tch.filesystem.tools;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class GetDataTool {
    public Set getData(String path, String cw){
        HashSet<Object> set = new HashSet<>();
        File file = new File(path);
        File[] files = file.listFiles();
        if(files.length  < 1){
            return set;
        }
        for (File f:files) {
            if(f.isDirectory()){
                HashMap<String, Object> map = new HashMap<>();
                if(cw.equals(f.getPath())){
                    continue;
                }else{
                    map.put("id",f.getPath());
                    map.put("name",f.getName());
                    map.put("child",getData(f.getPath(),cw));
                    set.add(map);
                }
            }
        }
        return set;
    }
}
