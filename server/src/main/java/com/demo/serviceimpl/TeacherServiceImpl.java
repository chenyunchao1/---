package com.demo.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.TeacherMapper;
import com.demo.pojo.Teacher;
import com.demo.service.TeacherService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper,Teacher> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public List<Teacher> findTeacher(Teacher teacher) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<> ();
        queryWrapper.like (!Strings.isBlank (teacher.getName ()),"teacher_name",teacher.getName ())
                .eq (teacher.getId () != null && teacher.getId () != 0 ,"id",teacher.getId ());
       return teacherMapper.selectList (queryWrapper);

    }

    @Override
    public List<Teacher> findByIds(List<Long> ids) {
        List<Teacher> teachers = teacherMapper.selectBatchIds (ids);
        return teachers;
    }

    @Override
    public List<Teacher> idPassword(String stuNio, String password){
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq("id",stuNio).eq("teacher_password",password);
        List<Teacher> teachers = teacherMapper.selectList(queryWrapper);
        return teachers;
    }
    @Override
    public Teacher findByonid(Long id) {
        Teacher teacher = teacherMapper.selectById (id);
        return teacher;
    }

    @Override
    public String updateTeacher(Teacher teacher) {
        int n = teacherMapper.updateById (teacher);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    @Override
    public String insertTeacher(Teacher teacher) {
        int n = teacherMapper.insert (teacher);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    public String deleteTeacher(Long id){
        int n = teacherMapper.deleteById (id);
        if(n == 0){
            return "error";
        }
        return "success";
    }


}
