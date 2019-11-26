package com.tch.filesystem;

import com.tch.filesystem.tools.CategoryTool;
import com.tch.filesystem.tools.ZipTool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@SpringBootTest
class FilesystemApplicationTests {

    @Autowired
    private ZipTool zipTool;

    @Test
    void contextLoads() throws Exception {
        String json = "\"123\"";
        System.out.println(json);

    }
}

