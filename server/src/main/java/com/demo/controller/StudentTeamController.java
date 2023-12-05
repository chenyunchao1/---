package com.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.pojo.StudentTeam;
import com.demo.service.StudentTeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping(value = "/studentTeam")
@Api(value = "团队成员管理")
public class StudentTeamController {
    
    @Autowired
    private StudentTeamService studentTeamService;
    
    @RequestMapping(path = "/find",method = RequestMethod.POST)
    @ApiOperation (value = "查询组队信息",notes = "根据组队id或者学生学号来进行分页查询")
    @ResponseBody
    public Page findPageStudentTeam(@RequestBody StudentTeam studentTeam , int pageNum, int pageSize) {
        return studentTeamService.findPageStudentTeam (studentTeam,pageNum,pageSize);
    }
    
    @RequestMapping(path = "/deleteStudentTeam",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "根据id删除学生组队信息",notes = "根据学生id，和队伍id，来删除信息")
    public String deleteStudentTeam(Long id,Long teamId){
        return studentTeamService.deleteStudentTeam (id,teamId);
    }

    @RequestMapping(path = "/insertStudentTeam",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "根据id查询学生信息",notes = "根据id，而不是学号，来删除信息")
    public Object insertStudentTeam(StudentTeam studentTeam){
        JSONObject jsonObject = new JSONObject();
        List<StudentTeam> studentTeams = studentTeamService.findByStudentTeam(studentTeam);
        if(studentTeams.size()>0){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "添加失败,队伍中已存在");
        }else {
            studentTeamService.insertStudentTeam (studentTeam);
            jsonObject.put("code",1);
            jsonObject.put("msg","添加成功");
        }
        return jsonObject;
    }

}
