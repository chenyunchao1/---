package com.demo.serviceimpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.ProductionCompetitionMapper;
import com.demo.pojo.ProductionCompetition;
import com.demo.service.ProductionCompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionCompetitionServiceImpl extends ServiceImpl<ProductionCompetitionMapper,ProductionCompetition> implements ProductionCompetitionService {

    @Autowired
    private ProductionCompetitionMapper productionCompetitionMapper;

    @Override
    public List<ProductionCompetition> findProductionCompetition(ProductionCompetition productionCompetition) {
        QueryWrapper<ProductionCompetition> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq (null!=productionCompetition.getCompetitionId (),"competition_id",productionCompetition.getCompetitionId ())
                .eq (null!=productionCompetition.getProductionId (),"production_id",productionCompetition.getProductionId ());
        return productionCompetitionMapper.selectList(queryWrapper);
    }

    @Override
    public List<ProductionCompetition> findByIds(List<Long> ids) {
        List<ProductionCompetition> productionCompetitions = productionCompetitionMapper.selectBatchIds (ids);
        return productionCompetitions;
    }

    @Override
    public ProductionCompetition findByonid(Long id) {
        ProductionCompetition productionCompetition = productionCompetitionMapper.selectById (id);
        return productionCompetition;
    }

    @Override
    public String updateProductionCompetition(ProductionCompetition productionCompetition) {
        int n = productionCompetitionMapper.updateById (productionCompetition);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    @Override
    public String insertProductionCompetition(ProductionCompetition productionCompetition) {
        int n = productionCompetitionMapper.insert (productionCompetition);
        if(n == 0 ){
            return "error";
        }
        return "success";
    }

    public String deleteProductionCompetition(Long id){
        int n = productionCompetitionMapper.deleteById (id);
        if(n == 0){
            return "error";
        }
        return "success";
    }


}
