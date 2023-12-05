package com.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.pojo.Competition;

import java.util.List;

public interface CompetitionService extends IService<Competition> {

    List<Competition> findByIds(List<Long> ids);

    Competition findByonid(Long id);

    String updateCompetition(Competition sompetition);

    String deleteCompetition(Long id);

    String insertCompetition(Competition sompetition);

    List<Competition> findCompetition(Competition sompetition);
}
