package com.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.constant.Constants;
import com.demo.pojo.Student;
import com.demo.pojo.Teacher;
import com.demo.service.StudentService;
import com.demo.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @autthor lc1100
 * @create 2022-03-25-14:10
 */


@Controller
@CrossOrigin

@Api(value = "登录")
public class LoginController {

    @Configuration
    public class MyPicConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            String os = System.getProperty("os.name");
            if (os.toLowerCase().startsWith("win")) { // windos系统
                registry.addResourceHandler("/img/avatorImages/**")
                        .addResourceLocations("file:" + Constants.RESOURCE_WIN_PATH + "\\img\\avatorImages\\");
            } else { // MAC、Linux系统
                registry.addResourceHandler("/img/avatorImages/**")
                        .addResourceLocations("file:" + Constants.RESOURCE_MAC_PATH + "/img/avatorImages/");
            }
        }
    }

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/user/login/status",method = RequestMethod.POST)
    @ApiOperation(value = "登录",notes = "返回权限、姓名、id")
    @ResponseBody
    public Object findOneTeacher(HttpServletRequest req, HttpSession session){
        JSONObject jsonObject = new JSONObject();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        List<Student> students = studentService.stuNioPassword(username,password);
        if(students.size()>0){
            jsonObject.put("code", 1);
            jsonObject.put("msg", "登录成功");
            jsonObject.put("permissions","student");
            jsonObject.put("userMsg", students.get(0));
            session.setAttribute("username", students.get(0).getName());
            return jsonObject;
        }
        List<Teacher> teachers = teacherService.idPassword(username,password);
        if(teachers.size()>0){
            jsonObject.put("code", 1);
            jsonObject.put("msg", "登录成功");
            jsonObject.put("permissions","teacher");
            jsonObject.put("userMsg", teachers.get(0));
            session.setAttribute("username", teachers.get(0).getName());
            return jsonObject;
        }

        jsonObject.put("code", 0);
        jsonObject.put("msg", "用户名或密码错误");
        return jsonObject;
    }
}
