package com.tch.filesystem.tools;

import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ZipTool {

    public boolean zip(ZipOutputStream zipOutputStream, File file, String name){
        try {
            if(!file.exists()){
                return false;
            }
            if(file.isDirectory()){
                zipOutputStream.putNextEntry(new ZipEntry(name + File.separator));
                File[] list = file.listFiles();
                for (File f:list) {
                    zip(zipOutputStream,f,name + File.separator + f.getName());
                }
            }else{
                zipOutputStream.putNextEntry(new ZipEntry(name));
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                int len;
                byte[] data = new byte[1024];
                while ((len = bufferedInputStream.read(data)) != -1){
                    zipOutputStream.write(data,0,len);
                }
                bufferedInputStream.close();
                fileInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
