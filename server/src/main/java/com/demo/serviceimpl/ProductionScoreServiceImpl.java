package com.demo.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.ProductionScoreMapper;
import com.demo.pojo.ProductionScore;
import com.demo.service.ProductionScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionScoreServiceImpl extends ServiceImpl<ProductionScoreMapper,ProductionScore> implements ProductionScoreService {

    @Autowired
    private ProductionScoreMapper productionScoreMapper;

    @Override
    public List<ProductionScore> findProductionScore(ProductionScore productionScore) {
        QueryWrapper<ProductionScore> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq (null!=productionScore.getScoreTypeId ()&&0!=productionScore.getScoreTypeId (),"score_type_id",productionScore.getScoreTypeId ())
                .eq (null!=productionScore.getProductionId ()&&0!=productionScore.getProductionId (),"production_id",productionScore.getProductionId ())
                .eq (null!=productionScore.getPersonId ()&&0!=productionScore.getPersonId (),"person_id",productionScore.getPersonId ())
                .eq (null!=productionScore.getPersonType ()&&0!=productionScore.getPersonType (),"person_type",productionScore.getPersonType ());
        return productionScoreMapper.selectList(queryWrapper);

    }

    @Override
    public List<ProductionScore> findByIds(List<Long> ids) {
        List<ProductionScore> productionScores = productionScoreMapper.selectBatchIds (ids);
        return productionScores;
    }

    @Override
    public ProductionScore findByonid(Long id) {
        ProductionScore productionScore = productionScoreMapper.selectById (id);
        return productionScore;
    }

    @Override
    public String updateProductionScore(ProductionScore productionScore) {
        int n = productionScoreMapper.updateById (productionScore);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    @Override
    public String insertProductionScore(ProductionScore productionScore) {
        int n = productionScoreMapper.insert (productionScore);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    public String deleteProductionScore(Long id){
        int n = productionScoreMapper.deleteById (id);
        if(n == 0){
            return "error";
        }
        return "success";
    }


}
