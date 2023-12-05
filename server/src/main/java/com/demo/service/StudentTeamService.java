package com.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.pojo.StudentTeam;

import java.util.List;

public interface StudentTeamService extends IService<StudentTeam> {

    List<StudentTeam> findByIds(List<Long> ids);

    List<StudentTeam> findByStudentTeam(StudentTeam studentTeam);

    StudentTeam findByonid(Long id);

    String updateStudentTeam(StudentTeam studentTeam);

    String deleteStudentTeam(Long stuId,Long teamId);

    String insertStudentTeam(StudentTeam studentTeam);

    Page findPageStudentTeam(StudentTeam studentTeam,int pageNum,int pageSize);

}
