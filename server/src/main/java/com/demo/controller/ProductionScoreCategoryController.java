package com.demo.controller;


import com.demo.pojo.ProductionScoreCategory;
import com.demo.service.ProductionCategoryService;
import com.demo.service.ProductionScoreCategoryService;
import com.demo.service.ScoreCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Controller
@RequestMapping(value = "/productionScoreCategory")
@Api(value = "作品评分、作品类别信息管理")
public class ProductionScoreCategoryController {

    @Autowired
    private ProductionScoreCategoryService productionScoreCategoryService;

    @Autowired
    private ProductionCategoryService productionCategoryService;

    @Autowired
    private ScoreCategoryService scoreCategoryService;

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation (value = "更新作品评分、作品类别信息",notes = "根据id,来修改其他信息")
    @ResponseBody
    public String updateProductionScoreCategory(ProductionScoreCategory productionScoreCategory) {
        return productionScoreCategoryService.updateProductionScoreCategory (productionScoreCategory);
    }

    @RequestMapping(path = "/find",method = RequestMethod.POST)
    @ApiOperation (value = "查询用户信息",notes = "根据姓名、班级、学号来进行分页查询")
    @ResponseBody
    public Object findPageProductionScoreCategory(@RequestParam Long style) {
        ProductionScoreCategory productionScoreCategory = new ProductionScoreCategory();
        productionScoreCategory.setProductionCategoryId(style);
        List<ProductionScoreCategory> lists = productionScoreCategoryService.findProductionScoreCategory (productionScoreCategory);
        List<Long> scoreCategoryIds = lists.stream().map(ProductionScoreCategory::getScoreCategoryId).collect(Collectors.toList());
        if(scoreCategoryIds.size() == 0){
            return "";
        }
        return scoreCategoryService.findByIds(scoreCategoryIds);

    }

    @RequestMapping(path = "/findOne",method = RequestMethod.POST)
    @ApiOperation (value = "根据id查询作品评分、作品类别信息",notes = "根据id，而不是学号，来查询其他信息")
    @ResponseBody
    public ProductionScoreCategory findOneProductionScoreCategory(Long id){
        return productionScoreCategoryService.findByonid (id);
    }

    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "根据id删除作品评分、作品类别信息",notes = "根据id，来删除信息")
    public String deleteProductionScoreCategory(ProductionScoreCategory productionScoreCategory){
        List<ProductionScoreCategory> list = productionScoreCategoryService.findProductionScoreCategory(productionScoreCategory);
        list.forEach(e -> productionScoreCategoryService.deleteProductionScoreCategory (e.getId()));
        return "success";
    }

    @RequestMapping(path = "/insertOne",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "添加作品评分、作品类别信息",notes = "根据id，而不是学号，来删除信息")
    public String insertProductionScoreCategory(ProductionScoreCategory productionScoreCategory){
        return productionScoreCategoryService.insertProductionScoreCategory (productionScoreCategory);
    }

}
