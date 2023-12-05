package com.demo.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.ProductionScoreCategoryMapper;
import com.demo.pojo.ProductionScoreCategory;
import com.demo.service.ProductionScoreCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionScoreCategoryServiceImpl extends ServiceImpl<ProductionScoreCategoryMapper,ProductionScoreCategory> implements ProductionScoreCategoryService {

    @Autowired
    private ProductionScoreCategoryMapper productionScoreCategoryMapper;

    @Override
    public List<ProductionScoreCategory> findProductionScoreCategory(ProductionScoreCategory productionScoreCategory) {
        QueryWrapper<ProductionScoreCategory> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq (null!=productionScoreCategory.getProductionCategoryId ()&&0 != productionScoreCategory.getProductionCategoryId (),
                "production_category_id",
                productionScoreCategory.getProductionCategoryId ())
                .eq (null!=productionScoreCategory.getScoreCategoryId ()&&0 != productionScoreCategory.getScoreCategoryId (),"score_category_id",
                        productionScoreCategory.getScoreCategoryId ());
        return productionScoreCategoryMapper.selectList (queryWrapper);

    }

    @Override
    public List<ProductionScoreCategory> findByIds(List<Long> ids) {
        List<ProductionScoreCategory> productionScoreCategorys = productionScoreCategoryMapper.selectBatchIds (ids);
        return productionScoreCategorys;
    }

    @Override
    public ProductionScoreCategory findByonid(Long id) {
        ProductionScoreCategory productionScoreCategory = productionScoreCategoryMapper.selectById (id);
        return productionScoreCategory;
    }

    @Override
    public String updateProductionScoreCategory(ProductionScoreCategory productionScoreCategory) {
        int n = productionScoreCategoryMapper.updateById (productionScoreCategory);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    @Override
    public String insertProductionScoreCategory(ProductionScoreCategory productionScoreCategory) {
        int n = productionScoreCategoryMapper.insert (productionScoreCategory);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    public String deleteProductionScoreCategory(Long id){
        int n = productionScoreCategoryMapper.deleteById (id);
        if(n == 0){
            return "error";
        }
        return "success";
    }


}
