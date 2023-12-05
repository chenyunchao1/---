package com.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.pojo.ScoreCategory;

import java.util.List;

public interface ScoreCategoryService extends IService<ScoreCategory> {

    List<ScoreCategory> findByIds(List<Long> ids);

    ScoreCategory findByonid(Long id);

    String updateScoreCategory(ScoreCategory scoreCategory);

    String deleteScoreCategory(Long id);

    String insertScoreCategory(ScoreCategory scoreCategory);

    List<ScoreCategory> findScoreCategory(ScoreCategory scoreCategory);
}
