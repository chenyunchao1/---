package com.demo.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.EvaluationMapper;
import com.demo.pojo.Evaluation;
import com.demo.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationServiceImpl extends ServiceImpl<EvaluationMapper,Evaluation> implements EvaluationService {

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Override
    public List<Evaluation> findEvaluation(Evaluation evaluation) {
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq (null!=evaluation.getEvaluationProductionId (),"evaluation_production_id",evaluation.getEvaluationProductionId ());
        return evaluationMapper.selectList(queryWrapper);
    }

    @Override
    public List<Evaluation> findByIds(List<Long> ids) {
        List<Evaluation> evaluations = evaluationMapper.selectBatchIds (ids);
        return evaluations;
    }

    @Override
    public Evaluation findByonid(Long id) {
        Evaluation evaluation = evaluationMapper.selectById (id);
        return evaluation;
    }

    @Override
    public String updateEvaluation(Evaluation evaluation) {
        int n = evaluationMapper.updateById (evaluation);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    @Override
    public String insertEvaluation(Evaluation evaluation) {
        int n = evaluationMapper.insert (evaluation);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    public String deleteEvaluation(Long id){
        int n = evaluationMapper.deleteById (id);
        if(n == 0){
            return "error";
        }
        return "success";
    }


}
