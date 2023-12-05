package com.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.pojo.ProductionCategory;

import java.util.List;


public interface ProductionCategoryService extends IService<ProductionCategory> {

    List<ProductionCategory> findByIds(List<Long> ids);

    ProductionCategory findByonid(Long id);

    String updateProductionCategory(ProductionCategory productionCategory);

    String deleteProductionCategory(Long id);

    String insertProductionCategory(ProductionCategory productionCategory);

    List<ProductionCategory> findProductionCategory(ProductionCategory productionCategory);

}
