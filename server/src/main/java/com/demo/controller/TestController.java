package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class TestController {

    @RequestMapping("/muban")
    public String index(){
        System.out.println ("sssss");
        return "muban";
    }

    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return "文件为空";
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            System.out.println ("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println ("文件的后缀名为：" + suffixName);
            // 设置文件存储路径
            String filePath = "D://aim//";
            String path = filePath + fileName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            return "success";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping(value = "/download")
    @ResponseBody
    public String Downlo(String fileName, HttpServletRequest request, HttpServletResponse response){
        System.out.println ("this url is " + fileName);
        String fileUrl = "D://aim//" + fileName;
        if(fileUrl != null){
            File file = new File (fileUrl);
            if(file.exists ()){
                response.setContentType ("application/force-download"); //设置强制下载不打开
                response.addHeader ("Content-Disposition","attachment;fileName=" + fileName);//设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream (file);
                    bis = new BufferedInputStream (fis);
                    OutputStream os = response.getOutputStream ();
                    int i  = bis.read (buffer);
                    while (i != -1){
                        os.write (buffer,0,i);
                        i = bis.read (buffer);
                    }
                    System.out.println ("success");
                } catch (Exception e) {
                    e.printStackTrace ();
                } finally {
                    if(bis != null){
                        try {
                            bis.close ();
                        } catch (IOException e) {
                            e.printStackTrace ();
                        }
                    }
                    if (fis != null){
                        try {
                            fis.close ();
                        } catch (IOException e) {
                            e.printStackTrace ();
                        }
                    }
                }
            }
        }
        return null;
    }

}
