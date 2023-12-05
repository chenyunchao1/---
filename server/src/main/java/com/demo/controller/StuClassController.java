package com.demo.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.pojo.StuClass;
import com.demo.service.StuClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/stuClass")
@Api(value = "班级信息管理")
public class StuClassController {

    @Autowired
    private StuClassService stuClassService;

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "更新班级信息",notes = "根据id,来修改其他信息")
    public String updateStuClass(@RequestBody StuClass stuClass) {
        return stuClassService.updateStuClass (stuClass);
    }

    @RequestMapping(path = "/find",method = RequestMethod.POST)
    @ApiOperation (value = "查询班级信息",notes = "根据班级名称进行分页查询")
    public Object findPageStuClass( StuClass stuClass ) {
        return stuClassService.findStuClass (stuClass);
    }

    @RequestMapping(path = "/findOne",method = RequestMethod.POST)
    @ApiOperation (value = "根据id查询班级信息",notes = "根据id，来查询其他信息")
    public StuClass findOneStuClass(@RequestBody Long id){
        return stuClassService.findByonid (id);
    }

    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    @ApiOperation (value = "根据id删除班级信息",notes = "根据id，来删除信息")
    public String deleteStuClass(@RequestBody Long id){
        return stuClassService.deleteStuClass (id);
    }

    @RequestMapping(path = "/insertOne",method = RequestMethod.POST)
    @ApiOperation (value = "根据id添加班级信息",notes = "根据id，来添加信息")
    public String insertStuClass(@RequestBody StuClass stuClass){
        return stuClassService.insertStuClass (stuClass);
    }
}
