package com.demo.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.TeamMapper;
import com.demo.pojo.Team;
import com.demo.service.TeamService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper,Team> implements TeamService {

    @Autowired
    private TeamMapper teamMapper;

    @Override
    public List<Team> findTeam(Team team) {
        QueryWrapper<Team> queryWrapper = new QueryWrapper<> ();
        queryWrapper.like (!Strings.isBlank (team.getTeamName ()),"team_name",team.getTeamName ());
        return teamMapper.selectList (queryWrapper);

    }

    @Override
    public List<Team> findByIds(List<Long> ids) {
        List<Team> teams = teamMapper.selectBatchIds (ids);
        return teams;
    }

    @Override
    public Team findByonid(Long id) {
        Team team = teamMapper.selectById (id);
        return team;
    }

    @Override
    public String updateTeam(Team team) {
        int n = teamMapper.updateById (team);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    @Override
    public String insertTeam(Team team) {
        int n = teamMapper.insert (team);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    public String deleteTeam(Long id){
        int n = teamMapper.deleteById (id);
        if(n == 0){
            return "error";
        }
        return "success";
    }


}
