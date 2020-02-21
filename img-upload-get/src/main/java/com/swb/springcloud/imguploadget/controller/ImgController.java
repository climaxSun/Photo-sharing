package com.swb.springcloud.imguploadget.controller;

import com.swb.springcloud.imguploadget.pojo.WangEditor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ImgController {

    @Value("${avatarUrl}")
    private String avatarUrl;

    @Value("${flowerUrl}")
    private String flowerUrl;

    @Value("${articleUrl}")
    private String articleUrl;

    @PostMapping(value = "/upload/userAvatar")
    public String HandleFileUpload(@RequestParam(value = "file",required = true) MultipartFile file){
        File fileDir = new File(avatarUrl);
        if (!fileDir.exists())
            fileDir.mkdirs();
        String fileName = UUID.randomUUID().toString() + ".jpg";
        File files = new File(fileDir + "/" + fileName);
        try {
            file.transferTo(files);
        } catch (IOException e) {
            e.printStackTrace();
            return "失败";
        }
        return fileName;
    }

    @PostMapping(value = "/upload/flowers")
    public List<String> HandleFileUpload(@RequestParam(value = "file_data",required = true) MultipartFile[] file_data){
        List<String> FileList= new ArrayList();
        File fileDir = new File(flowerUrl);
        if (!fileDir.exists())
            fileDir.mkdirs();
        try {
            for (int i = 0; i < file_data.length; i++) {
                if (file_data[i].isEmpty())
                    continue;
                String fileName = UUID.randomUUID().toString() + ".jpg";
                File files = new File(fileDir + "/" + fileName);
                file_data[i].transferTo(files);
                FileList.add(fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return FileList;
    }

    @PostMapping(value = "/delete/flowers")
    public String delete(@RequestParam(value="photoName[]")String[] photoName ){
        for (String s:photoName){
            File file=new File(flowerUrl+s);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
        return "ok";
    }

    @PostMapping(value = "/upload/article")
    public WangEditor articleFileUpload(@RequestParam(value = "articleFile",required = true) MultipartFile[] articleFile){
        String[] data=new String[5];
        File fileDir = new File(articleUrl);
        int j=0;
        try {
            for (int i = 0; i < articleFile.length; i++) {
                if (articleFile[i].isEmpty())
                    continue;
                String prefix=articleFile[i].getOriginalFilename().substring(articleFile[i].getOriginalFilename().lastIndexOf(".")+1);
                String fileName = UUID.randomUUID().toString() +"."+ prefix;
                File files = new File(fileDir + "/" + fileName);
                articleFile[i].transferTo(files);
                data[j]="/image/article/"+fileName;
                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        WangEditor wangEditor=new WangEditor(data);
        return wangEditor;
    }

}
