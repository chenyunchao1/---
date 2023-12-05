package com.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.pojo.Picture;
import com.demo.pojo.Production;
import com.demo.pojo.StudentTeam;
import com.demo.service.PictureService;
import com.demo.service.ProductionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @autthor lc1100
 * @create 2022-03-28-16:34
 */


@Controller
@CrossOrigin
public class PictureController {

    @Autowired
    public static Long staProductId;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private ProductionService productionService;

    @RequestMapping(value = "/picTure/findByPictureID")
    @ApiOperation(value = "根据图片所属作品的Id查询作品列表")
    @ResponseBody
    public Object findByPictureID(HttpServletRequest req, @RequestParam String pictureId){
        Picture picture = new Picture();
        picture.setProductionId(Long.parseLong(pictureId));
        List<Picture> teamList =pictureService.findPicture(picture);
        return teamList;
    }

    @RequestMapping(value = "/picTure/deletePicture")
    @ApiOperation(value = "根据id删除图片")
    @ResponseBody
    public Object deletePicture( @RequestParam Long id){
        Picture picture= pictureService.findByonid(id);
        File file = new File(System.getProperty("user.dir") + "\\server"+picture.getPictureUrl());
        if (!file.exists()){
            System.out.println("删除失败，文件不存在");
            return "error";
        }
        return pictureService.deletePicture(id);
    }

    @ResponseBody
    @RequestMapping(value = "/picTure/insertPic", method = RequestMethod.POST)
    public Object updateUserPic(@RequestParam("file") MultipartFile avatorFile, Long productionId){
        JSONObject jsonObject = new JSONObject();
        if (productionId == -1)
            productionId = staProductId;
        if (avatorFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败！");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + "\\server\\" + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "productPic" ;
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeAvatorPath = "/img/productPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Picture picture = new Picture();
            picture.setProductionId(productionId);
            picture.setPictureUrl(storeAvatorPath);
            picture.setCreatTime(new Date());
            String res = pictureService.insertPicture(picture);
            if ("success".equals(res)){
                jsonObject.put("code", 1);
                jsonObject.put("msg", "上传成功");
                return jsonObject;
            }else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "上传失败");
                return jsonObject;
            }
        }catch (IOException e){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败"+e.getMessage());
            return jsonObject;
        }finally {
            return jsonObject;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/production/video", method = RequestMethod.POST)
    public Object updateVideo(@RequestParam("file") MultipartFile avatorFile, Long productionId){
        JSONObject jsonObject = new JSONObject();
        if (productionId == -1)
            productionId = staProductId;
        Production re = new Production();
        if (avatorFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败！");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + "\\server\\" + System.getProperty("file.separator")  + "video" ;
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeAvatorPath = "/video/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Production production = new Production();
            production.setId(productionId);
            production.setProductionVideo(storeAvatorPath);
            re = productionService.findByOnid(productionId);

            String res = productionService.updateProduction(production);
            if ("success".equals(res)){
                jsonObject.put("code", 1);
                jsonObject.put("msg", "上传成功");

            }else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "上传失败");

            }
        }catch (IOException e){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败"+e.getMessage());
        }
        if(re.getProductionVideo() != null) {
            File file = new File(System.getProperty("user.dir") + "\\server" + re.getProductionVideo());
            if (!file.exists()) {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "原视频不存在");
                return jsonObject;
            }else {
                file.delete();
            }
        }
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "/production/document", method = RequestMethod.POST)
    public Object updateDocument(@RequestParam("file") MultipartFile avatorFile, Long productionId){
        JSONObject jsonObject = new JSONObject();
        if (productionId == -1)
           productionId = staProductId;
        Production re = new Production();
        if (avatorFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败！");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + "\\server\\" + System.getProperty("file.separator")  + "document" ;
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeAvatorPath = "/document/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Production production = new Production();
            if (productionId != -1)
                production.setId(productionId);
            else
                production.setId(staProductId);
            production.setProductionDocument(storeAvatorPath);
            re = productionService.findByOnid(productionId);

            String res = productionService.updateProduction(production);
            if ("success".equals(res)){
                jsonObject.put("code", 1);
                jsonObject.put("msg", "上传成功");

            }else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "上传失败");

            }
        }catch (IOException e){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败"+e.getMessage());
        }
        if(re.getProductionVideo() != null) {
            File file = new File(System.getProperty("user.dir") + "\\server" + re.getProductionDocument());
            if (!file.exists()) {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "原视频不存在");
                return jsonObject;
            }else {
                file.delete();
            }
        }
        return jsonObject;
    }
    @ResponseBody
    @RequestMapping(value = "/production/titlePicture", method = RequestMethod.POST)
    public Object updateTitlePicture(@RequestParam("file") MultipartFile avatorFile, Long productionId){
        JSONObject jsonObject = new JSONObject();
        if (productionId == -1)
            productionId = staProductId;
        if (avatorFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败！");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + "\\server\\" + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "productPic" ;
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeAvatorPath = "/img/productPic/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Production production = new Production();
            production.setId(productionId);
            production.setProductionTitlePicture(storeAvatorPath);
            production.setCreatTime(new Date());
            String res = productionService.updateProduction(production);
            if ("success".equals(res)){
                jsonObject.put("code", 1);
                jsonObject.put("msg", "上传成功");
                return jsonObject;
            }else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "上传失败");
                return jsonObject;
            }
        }catch (IOException e){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败"+e.getMessage());
            return jsonObject;
        }finally {
            return jsonObject;
        }
    }

}
