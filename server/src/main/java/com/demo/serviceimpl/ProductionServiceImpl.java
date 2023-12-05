package com.demo.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.ProductionMapper;
import com.demo.pojo.Production;
import com.demo.service.ProductionService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionServiceImpl extends ServiceImpl<ProductionMapper,Production> implements ProductionService {

    @Autowired
    private ProductionMapper productionMapper;

    @Override
    public Page findPageProduction(Production production, int pageNum, int pageSize) {
        Page<Production> page = new Page<> (pageNum,pageSize);
        QueryWrapper<Production> queryWrapper = new QueryWrapper<> ();
        queryWrapper.like (!Strings.isBlank (production.getProductionName ()),"production_name",production.getProductionName ())
                .eq (null!=production.getCategoryId ()&&0!=production.getCategoryId (),"category_id",production.getCategoryId ())
                .eq (null!=production.getId ()&&0!=production.getId (),"id",production.getId ());
        productionMapper.selectPage (page,queryWrapper);
        return page;
    }

    @Override
    public List<Production> findByTeamIds(List<Long> teamIds,Long type) {
        QueryWrapper<Production> queryWrapper = new QueryWrapper<> ();
        queryWrapper.in("team_id",teamIds).eq("team_type",type);
        List<Production> productions = productionMapper.selectList (queryWrapper);
        return productions;
    }

    @Override
    public List<Production> findByProduction(Production production){
        QueryWrapper<Production> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq (null!=production.getTeamId()&&0!=production.getTeamId(),"team_id",production.getTeamId ())
                .eq (null!=production.getTeamType ()&&0!=production.getTeamType (),"team_type",production.getTeamType ())
                .like (!Strings.isBlank (production.getProductionName ()),"production_name",production.getProductionName ())
                .eq (null!=production.getCategoryId ()&&0!=production.getCategoryId (),"category_id",production.getCategoryId ())
                .eq (null!=production.getId ()&&0!=production.getId (),"id",production.getId ())
                .eq (null!=production.getProductionTeacherId ()&&0!=production.getProductionTeacherId (),"production_teacher_id",production.getProductionTeacherId ());
        return productionMapper.selectList(queryWrapper);
    }

    @Override
    public Production findByOnid(Long id) {
        Production production = productionMapper.selectById (id);
        return production;
    }

    @Override
    public String updateProduction(Production production) {
        int n = productionMapper.updateById (production);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    @Override
    public String insertProduction(Production production) {
        int n = productionMapper.insert (production);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    public String deleteProduction(Long id){
        int n = productionMapper.deleteById (id);
        if(n == 0){
            return "error";
        }
        return "success";
    }


}
