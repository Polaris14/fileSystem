package com.tch.filesystem.tools;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class CategoryTool {
    //文件分类
    private final static String[] videos = {"mp4","avi","rmvb"};
    private final static String[] images = {"jpg","png","bmp","gif","tif"};
    private final static String[] documents = {"txt","wps","doc","docx"};
    private final static String[] musics = {"mp3","midi","wma","vqf","amr"};

    public String cate(String cate){
        String gs = cate.toLowerCase();
        Collection video = ArrayToList(videos);
        Collection image = ArrayToList(images);
        Collection document = ArrayToList(documents);
        Collection music = ArrayToList(musics);
        if(video.contains(gs)){
            return "video";
        }
        if(image.contains(gs)){
            return "image";
        }
        if(document.contains(gs)){
            return "document";
        }
        if(music.contains(gs)){
            return "music";
        }
        return null;
    }

    public Collection ArrayToList(String[] arr){
        ArrayList list = new ArrayList<String>();
        for (int i = 0; i < arr.length; i++){
            list.add(arr[i]);
        }
        return list;
    }
}
