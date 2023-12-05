package com.demo.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.ScoreCategoryMapper;
import com.demo.pojo.ScoreCategory;
import com.demo.service.ScoreCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreCategoryServiceImpl extends ServiceImpl<ScoreCategoryMapper,ScoreCategory> implements ScoreCategoryService {

    @Autowired
    private ScoreCategoryMapper scoreCategoryMapper;

    @Override
    public List<ScoreCategory> findScoreCategory(ScoreCategory scoreCategory) {
        QueryWrapper<ScoreCategory> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq (null!=scoreCategory.getScoreSys (),"score_sys",scoreCategory.getScoreSys ())
                .eq(null!=scoreCategory.getScoreName(),"score_name",scoreCategory.getScoreName());
        return scoreCategoryMapper.selectList (queryWrapper);
    }

    @Override
    public List<ScoreCategory> findByIds(List<Long> ids) {
        List<ScoreCategory> scoreCategorys = scoreCategoryMapper.selectBatchIds (ids);
        return scoreCategorys;
    }

    @Override
    public ScoreCategory findByonid(Long id) {
        ScoreCategory scoreCategory = scoreCategoryMapper.selectById (id);
        return scoreCategory;
    }

    @Override
    public String updateScoreCategory(ScoreCategory scoreCategory) {
        int n = scoreCategoryMapper.updateById (scoreCategory);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    @Override
    public String insertScoreCategory(ScoreCategory scoreCategory) {
        int n = scoreCategoryMapper.insert (scoreCategory);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    public String deleteScoreCategory(Long id){
        int n = scoreCategoryMapper.deleteById (id);
        if(n == 0){
            return "error";
        }
        return "success";
    }


}
