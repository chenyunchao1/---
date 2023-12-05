package com.demo.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.StudentTeamMapper;
import com.demo.pojo.StudentTeam;
import com.demo.service.StudentTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentTeamServiceImpl extends ServiceImpl<StudentTeamMapper,StudentTeam> implements StudentTeamService {

    @Autowired
    private StudentTeamMapper studentTeamMapper;

    @Override
    public Page findPageStudentTeam(StudentTeam studentTeam, int pageNum, int pageSize) {
        Page<StudentTeam> page = new Page<> (pageNum,pageSize);
        QueryWrapper<StudentTeam> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq (null!=studentTeam.getStudentId (),"student_id",studentTeam.getStudentId ())
                .eq (null!=studentTeam.getTeamId (),"team_id",studentTeam.getTeamId ());
        studentTeamMapper.selectPage (page,queryWrapper);
        return page;
    }
    @Override
    public List<StudentTeam>  findByStudentTeam(StudentTeam studentTeam) {
        QueryWrapper<StudentTeam> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq (null!=studentTeam.getStudentId (),"student_id",studentTeam.getStudentId ())
                .eq (null!=studentTeam.getTeamId (),"team_id",studentTeam.getTeamId ());
        return studentTeamMapper.selectList(queryWrapper);
    }

    @Override
    public List<StudentTeam> findByIds(List<Long> ids) {
        List<StudentTeam> studentTeams = studentTeamMapper.selectBatchIds (ids);
        return studentTeams;
    }

    @Override
    public StudentTeam findByonid(Long id) {
        StudentTeam studentTeam = studentTeamMapper.selectById (id);
        return studentTeam;
    }

    @Override
    public String updateStudentTeam(StudentTeam studentTeam) {
        int n = studentTeamMapper.updateById (studentTeam);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    @Override
    public String insertStudentTeam(StudentTeam studentTeam) {
        int n = studentTeamMapper.insert (studentTeam);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    @Override
    public String deleteStudentTeam(Long stuId,Long teamId){
        QueryWrapper<StudentTeam> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq (stuId!=null && stuId != 0,"student_id",stuId)
                .eq (null!=teamId && 0 != teamId,"team_id",teamId);
        int n = studentTeamMapper.delete (queryWrapper);
        if(n == 0){
            return "error";
        }
        return "success";
    }


}
