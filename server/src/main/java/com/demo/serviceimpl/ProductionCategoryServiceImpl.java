package com.demo.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.ProductionCategoryMapper;
import com.demo.pojo.ProductionCategory;
import com.demo.service.ProductionCategoryService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionCategoryServiceImpl extends ServiceImpl<ProductionCategoryMapper,ProductionCategory> implements ProductionCategoryService {

    @Autowired
    private ProductionCategoryMapper productionCategoryMapper;

    @Override
    public List<ProductionCategory> findProductionCategory(ProductionCategory productionCategory) {
        QueryWrapper<ProductionCategory> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq (null != productionCategory.getId () && 0 != productionCategory.getId (),"id",productionCategory.getId ())
                .like (!Strings.isBlank (productionCategory.getCategoryName ()),"category_name",productionCategory.getCategoryName ());
        return productionCategoryMapper.selectList (queryWrapper);

    }

    @Override
    public List<ProductionCategory> findByIds(List<Long> ids) {
        List<ProductionCategory> productionCategorys = productionCategoryMapper.selectBatchIds (ids);
        return productionCategorys;
    }

    @Override
    public ProductionCategory findByonid(Long id) {
        ProductionCategory productionCategory = productionCategoryMapper.selectById (id);
        return productionCategory;
    }

    @Override
    public String updateProductionCategory(ProductionCategory productionCategory) {
        int n = productionCategoryMapper.updateById (productionCategory);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    @Override
    public String insertProductionCategory(ProductionCategory productionCategory) {
        int n = productionCategoryMapper.insert (productionCategory);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    public String deleteProductionCategory(Long id){
        int n = productionCategoryMapper.deleteById (id);
        if(n == 0){
            return "error";
        }
        return "success";
    }


}
