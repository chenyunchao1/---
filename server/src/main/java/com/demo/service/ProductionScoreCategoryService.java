package com.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.pojo.ProductionScoreCategory;

import java.util.List;

public interface ProductionScoreCategoryService extends IService<ProductionScoreCategory> {

    List<ProductionScoreCategory> findByIds(List<Long> ids);

    ProductionScoreCategory findByonid(Long id);

    String updateProductionScoreCategory(ProductionScoreCategory productionScoreCategory);

    String deleteProductionScoreCategory(Long id);

    String insertProductionScoreCategory(ProductionScoreCategory productionScoreCategory);

    List<ProductionScoreCategory> findProductionScoreCategory(ProductionScoreCategory productionScoreCategory);
}
