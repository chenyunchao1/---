package com.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.constant.Constants;
import com.demo.pojo.Production;
import com.demo.service.ProductionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;


@CrossOrigin
@Controller
@Api(value = "作品信息管理")
public class ProductionController {

    @Autowired
    private ProductionService productionService;


    @RequestMapping(value = "/production/update",method = RequestMethod.POST)
    @ApiOperation (value = "更新作品信息",notes = "根据id,来修改其他信息")
    @ResponseBody
    public String updateProduction(Production production) {
        return productionService.updateProduction (production);
    }

    @RequestMapping(path = "/production/find",method = RequestMethod.POST)
    @ApiOperation (value = "查询用户信息",notes = "根据姓名、班级、学号来进行分页查询")
    @ResponseBody
    public List<Production> findPageProduction(Production params) {
//            String productionName = req.getParameter("productionName");
//            String categoryId = req.getParameter("categoryId");
//            String productionTeacherId =req.getParameter("productionTeacherId");
//            String studentId = req.getParameter("studentId");
//            Production production = new Production ();
//            if(productionName.isEmpty())
//                production.setProductionName(productionName);
//            if(categoryId.isEmpty())
//                production.setCategoryId(Long.parseLong(categoryId));
//            if(productionTeacherId.isEmpty())
//            production.setProductionTeacherId(Long.parseLong(productionTeacherId));
//        production.setTeamId();
        return productionService.findByProduction (params);
    }

    @RequestMapping(path = "/production/findOne",method = RequestMethod.POST)
    @ApiOperation (value = "根据id查询作品信息",notes = "根据id，而不是学号，来查询其他信息")
    @ResponseBody
    public Production findOneProduction( Long productId){
        return productionService.findByOnid (productId);
    }

    @RequestMapping(path = "/production/delete",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "根据id删除作品信息",notes = "根据id，而不是学号，来删除信息")
    public String deleteProduction(@RequestBody Long id){
        return productionService.deleteProduction (id);
    }

    @RequestMapping(path = "/production/insertOne",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "添加作品信息",notes = "根据id，而不是学号，来删除信息")
    public Object insertProduction(Production production){
        productionService.insertProduction (production);
        PictureController.staProductId = production.getId();
        return production;
    }

//    @ResponseBody
//    @RequestMapping(value = "/production/insertOne")
//    public Object updateDocument(@RequestParam("file") List<MultipartFile> avatorFile, Production production){
//        JSONObject jsonObject = new JSONObject();
//
//        return jsonObject;
//    }
}
