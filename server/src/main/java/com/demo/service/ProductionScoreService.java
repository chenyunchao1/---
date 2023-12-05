package com.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.pojo.ProductionScore;

import java.util.List;


public interface ProductionScoreService extends IService<ProductionScore> {
    List<ProductionScore> findByIds(List<Long> ids);

    ProductionScore findByonid(Long id);

    String updateProductionScore(ProductionScore productionScore);

    String deleteProductionScore(Long id);

    String insertProductionScore(ProductionScore productionScore);

    List<ProductionScore> findProductionScore(ProductionScore productionScore);
}
