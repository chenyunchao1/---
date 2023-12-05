package com.demo.controller;

import com.demo.pojo.Teacher;
import com.demo.service.StudentService;
import com.demo.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@Api(value = "教师信息管理")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/teacher/update",method = RequestMethod.POST)
    @ApiOperation (value = "更新教师信息",notes = "根据id,来修改其他信息")
    @ResponseBody
    public String updateTeacher(Teacher teacher) {
        return teacherService.updateTeacher (teacher);
    }

    @RequestMapping(path = "/teacher/find",method = RequestMethod.POST)
    @ApiOperation (value = "查询用户信息",notes = "根据教师id，或者姓名来进行分页查询")
    @ResponseBody
    public Object findPageTeacher(Teacher teacher ) {
        //        Teacher teacher = new Teacher ();
        //        Teacher teacher1 = new Teacher (null,"2","3",5L,6L);
        //        Teacher teacher2 = new Teacher (1L,"2","3",5L,6L);
        //        List<Teacher> list = Arrays.asList (new Teacher[]{teacher1, teacher2});
        //        teacherService.saveOrUpdateBatch (list,2);
        List<Teacher> lists = teacherService.findTeacher (teacher);
        lists.forEach(e -> e.setTeacherPassword(""));
        return lists;
    }

    @RequestMapping(path = "/teacher/findOne",method = RequestMethod.POST)
    @ApiOperation (value = "根据id查询教师信息",notes = "根据id，来查询其他信息")
    @ResponseBody
    public Teacher findOneTeacher(@RequestParam Long id){
        return teacherService.findByonid (id);
    }

    @RequestMapping(path = "/teacher/delete",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "根据id删除教师信息",notes = "根据id，来删除信息")
    public String deleteTeacher(@RequestBody Long id){
        return teacherService.deleteTeacher (id);
    }

    @RequestMapping(path = "/teacher/insertOne",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "添加教师信息",notes = "根据id，来删除信息")
    public String insertTeacher(@RequestBody Teacher teacher){
        return teacherService.insertTeacher (teacher);
    }



}
