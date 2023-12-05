package com.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.pojo.Production;

import java.util.List;

public interface ProductionService extends IService<Production> {

    List<Production> findByTeamIds(List<Long> ids,Long type);

    List<Production> findByProduction(Production production);

    Production findByOnid(Long id);

    String updateProduction(Production production);

    String deleteProduction(Long id);

    String insertProduction(Production production);

    Page findPageProduction(Production production,int pageNum,int pageSize);
}
