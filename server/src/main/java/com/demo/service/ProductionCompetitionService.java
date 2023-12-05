package com.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.pojo.ProductionCompetition;

import java.util.List;

public interface ProductionCompetitionService extends IService<ProductionCompetition> {

    List<ProductionCompetition> findByIds(List<Long> ids);

    ProductionCompetition findByonid(Long id);

    String updateProductionCompetition(ProductionCompetition productionCompetition);

    String deleteProductionCompetition(Long id);

    String insertProductionCompetition(ProductionCompetition productionCompetition);

    List<ProductionCompetition> findProductionCompetition(ProductionCompetition productionCompetition);
}
