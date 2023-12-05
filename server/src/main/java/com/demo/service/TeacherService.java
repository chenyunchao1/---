package com.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.pojo.Teacher;

import java.util.List;

public interface TeacherService extends IService<Teacher> {

    List<Teacher> findByIds(List<Long> ids);

    Teacher findByonid(Long id);

    List<Teacher> idPassword(String stuNio, String password);

    String updateTeacher(Teacher teacher);

    String deleteTeacher(Long id);

    String insertTeacher(Teacher teacher);

    List<Teacher> findTeacher(Teacher teacher);

}