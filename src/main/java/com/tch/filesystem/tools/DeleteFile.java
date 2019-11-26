package com.tch.filesystem.tools;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DeleteFile {
    public boolean deleteFile(File file){
        if(!file.exists()){
            return false;
        }
        if(file.isDirectory()){
            for (File f: file.listFiles()) {
                deleteFile(f);
            }
        }
        return file.delete();
    }
}
