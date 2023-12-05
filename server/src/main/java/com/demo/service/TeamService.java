package com.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.pojo.Team;

import java.util.List;

public interface TeamService extends IService<Team> {

    List<Team> findByIds(List<Long> ids);

    Team findByonid(Long id);

    String updateTeam(Team team);

    String deleteTeam(Long id);

    String insertTeam(Team team);

    List<Team> findTeam(Team team);
}
