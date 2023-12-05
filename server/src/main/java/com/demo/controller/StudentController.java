package com.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.pojo.Production;
import com.demo.pojo.Student;
import com.demo.pojo.StudentTeam;
import com.demo.pojo.Team;
import com.demo.service.ProductionService;
import com.demo.service.StudentService;
import com.demo.service.StudentTeamService;
import com.demo.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Controller
@RequestMapping(value = "/student")
@Api(value = "学生信息管理")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private ProductionService productionService;

    @Autowired
    private StudentTeamService studentTeamService;

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation (value = "更新学生信息",notes = "根据id,来修改其他信息")
    @ResponseBody
    public String updateStudent(Student student) {
        return studentService.updateStudent (student);
    }

    @RequestMapping(path = "/deleteStudentTeam",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "根据id删除学生组队信息",notes = "根据学生id，和队伍id，来删除信息")
    public String deleteStudentTeam(@RequestParam Long id,Long teamId){
        String str = studentTeamService.deleteStudentTeam (id,teamId);
        StudentTeam studentTeam = new StudentTeam();
        studentTeam.setTeamId(teamId);
        List<StudentTeam> list = studentTeamService.findByStudentTeam(studentTeam);
        if(list.size() == 0){
            teamService.deleteTeam(teamId);
        }
        return str;
    }

    @RequestMapping(path = "/find",method = RequestMethod.POST)
    @ApiOperation (value = "查询用户信息",notes = "根据姓名、班级、学号来进行分页查询")
    @ResponseBody
    public List<Student> findPageStudent(Student student) {
         return studentService.findStudent (student);
    }

    @RequestMapping(value = "/stuIdOfTeam")
    @ApiOperation (value = "根据id查询该学生所加入的队伍",notes = "根据id，而不是学号，来查询其他信息")
    @ResponseBody
    public Object stuIdOfTeam(HttpServletRequest req,@RequestParam Long id){
        JSONObject jsonObject = new JSONObject();
        StudentTeam studentTeam = new StudentTeam();
        studentTeam.setStudentId(id);
        List<StudentTeam> studentTeams = studentTeamService.findByStudentTeam(studentTeam);
        List<Long> teamIds = studentTeams.stream().map(StudentTeam::getTeamId).collect(Collectors.toList());
        List<Team> teamList = teamService.findByIds(teamIds);
        List<Production> productions = productionService.findByTeamIds(teamIds,1L);
        jsonObject.put("stuTeam",teamList);
        jsonObject.put("productionList",productions);
        return jsonObject;
    }
    @RequestMapping(value = "/stuIdByTeam")
    @ApiOperation (value = "根据队伍ID查询队伍里面的学生")
    @ResponseBody
    public Object stuIdByTeam(HttpServletRequest req,@RequestParam Long teamId){
        StudentTeam studentTeam = new StudentTeam();
        List<Student> studentList = new ArrayList<>();
        studentTeam.setTeamId(teamId);
        List<StudentTeam> teamList =studentTeamService.findByStudentTeam(studentTeam);
        List<Long> stuIds = teamList.stream().map(StudentTeam::getStudentId).collect(Collectors.toList());
        if(stuIds.size() == 0){
            return  studentList;
        }
        List<Student> students =  studentService.findByIds(stuIds);
        students.forEach(e -> e.setPassword(""));
        return students;
    }

    @RequestMapping(value = "/findOne")
    @ApiOperation (value = "根据id查询学生信息",notes = "根据id，而不是学号，来查询其他信息")
    @ResponseBody
    public Student findOneStudent(@RequestParam Long id){
        return studentService.findByonid (id);
    }

    @RequestMapping(value = "/myProduction")
    @ApiOperation (value = "根据id查询学生信息",notes = "根据id，而不是学号，来查询其他信息")
    @ResponseBody
    public Object myProduction(@RequestBody List<StudentTeam> stuTeamList){
        List<Long> teamId = stuTeamList.stream().map(StudentTeam::getTeamId).collect(Collectors.toList());
        return studentTeamService.findByIds(teamId);
    }

    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "根据id删除学生信息",notes = "根据id，而不是学号，来删除信息")
    public String deleteStudent(Long id){
        return studentService.deleteStudent (id);
    }

    @RequestMapping(path = "/insertOne",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "添加学生信息",notes = "根据id，而不是学号，来删除信息")
    public String insertStudent(@RequestBody Student student){
        return studentService.insertStudent (student);
    }

    @PostMapping("/insertStudentForExcel")
    @ResponseBody
    @ApiOperation (value = "从excel中导入学生信息")
    public Object insertStudentForExcel(@RequestParam("file") MultipartFile excelFile){
        JSONObject jsonObject = new JSONObject();
        String str = studentService.insertForExcel (excelFile);
        if("success".equals(str)){
            jsonObject.put("msg",str);
            jsonObject.put("code",1);
        }else{
            jsonObject.put("msg",str);
            jsonObject.put("code",0);
        }
        return jsonObject;
    }
}
