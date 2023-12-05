package com.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.pojo.Student;
import com.demo.pojo.StudentTeam;
import com.demo.pojo.Team;
import com.demo.service.StudentService;
import com.demo.service.StudentTeamService;
import com.demo.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Controller
@RequestMapping(value = "/team")
@Api(value = "团队信息管理")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private StudentTeamService studentTeamService;

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "更新团队信息",notes = "根据id,来修改其他信息")
    @ResponseBody
    public String updateTeam(@RequestBody Team team) {
        return teamService.updateTeam (team);
    }

    @RequestMapping(path = "/find",method = RequestMethod.POST)
    @ApiOperation (value = "查询团队信息",notes = "根据队名、id来进行分页查询")
    @ResponseBody
    public List<Team> findPageTeam(Team team) {
        return teamService.findTeam (team);
    }

    @RequestMapping(path = "/findTeam",method = RequestMethod.POST)
    @ApiOperation (value = "查询团队信息",notes = "根据队名、id来进行分页查询")
    @ResponseBody
    public List<Team> findTeam(Team team,Long stuNio) {
        Student student = new Student();
        List<Student> studentList = null;
        List<Team> teamList = new ArrayList<>();
        if(stuNio != null){
            student.setStuNio(stuNio);
           studentList = studentService.findStudent(student);
           StudentTeam studentTeam = new StudentTeam();
           studentTeam.setStudentId(studentList.get(0).getId());
           List<StudentTeam> studentTeamList = studentTeamService.findByStudentTeam(studentTeam);
           List<Long> teamIds = studentTeamList.stream().map(StudentTeam::getTeamId).collect(Collectors.toList());
           if(teamIds.size()>0)
            return teamService.findByIds(teamIds);
           else
               return teamList;
        }
        return teamService.findTeam (team);
    }


    @RequestMapping(path = "/findOne",method = RequestMethod.POST)
    @ApiOperation (value = "根据id查询团队信息",notes = "根据id，来查询其他信息")
    @ResponseBody
    public Team findOneTeam(@RequestBody Long id){
        return teamService.findByonid (id);
    }

    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "根据id查询团队信息",notes = "根据id，来删除信息,或根据团队teamId来批量删除")
    public String deleteTeam(@RequestBody Long studentId,Long teamId){
        return studentTeamService.deleteStudentTeam (studentId,teamId);
    }

    @RequestMapping(path = "/insertOne",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "创建团队信息",notes = "根据id，来创建信息，并添加团队成员")
    public Object insertTeam(Team team){
        String re = teamService.insertTeam (team);
         return team;
    }

}