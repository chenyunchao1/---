package com.demo.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.CompetitionMapper;
import com.demo.pojo.Competition;
import com.demo.service.CompetitionService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper,Competition> implements CompetitionService {

    @Autowired
    private CompetitionMapper competitionMapper;

    @Override
    public List<Competition> findCompetition(Competition competition) {
        QueryWrapper<Competition> queryWrapper = new QueryWrapper<> ();
        queryWrapper.like (null != competition.getCompetitionName (),"competition_name",competition.getCompetitionName ())
                .eq (null!=competition.getCompetitionCreatId (),"competition_creat_id",competition.getCompetitionCreatId ())
        ;
        return competitionMapper.selectList(queryWrapper);
    }

    @Override
    public List<Competition> findByIds(List<Long> ids) {
        List<Competition> competitions = competitionMapper.selectBatchIds (ids);
        return competitions;
    }

    @Override
    public Competition findByonid(Long id) {
        Competition competition = competitionMapper.selectById (id);
        return competition;
    }

    @Override
    public String updateCompetition(Competition competition) {
        int n = competitionMapper.updateById (competition);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    @Override
    public String insertCompetition(Competition competition) {
        int n = competitionMapper.insert (competition);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    public String deleteCompetition(Long id){
        int n = competitionMapper.deleteById (id);
        if(n == 0){
            return "error";
        }
        return "success";
    }


}
