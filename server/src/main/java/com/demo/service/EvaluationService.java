package com.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.pojo.Evaluation;

import java.util.List;

public interface EvaluationService extends IService<Evaluation> {

    List<Evaluation> findByIds(List<Long> ids);

    Evaluation findByonid(Long id);

    String updateEvaluation(Evaluation evaluation);

    String deleteEvaluation(Long id);

    String insertEvaluation(Evaluation evaluation);

    List<Evaluation> findEvaluation(Evaluation evaluation);
}
