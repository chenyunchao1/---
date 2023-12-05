package com.demo.controller;

import com.demo.pojo.ProductionCategory;
import com.demo.pojo.ProductionScoreCategory;
import com.demo.service.ProductionCategoryService;
import com.demo.service.ProductionScoreCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping(value = "/productionCategory")
@Api(value = "作品分组信息管理")
public class ProductionCategoryController {

    @Autowired
    private ProductionCategoryService productionCategoryService;

    @Autowired
    private ProductionScoreCategoryService productionScoreCategoryService;

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation (value = "更新作品分组信息",notes = "根据id,来修改其他信息")
    @ResponseBody
    public String updateProductionCategory(ProductionCategory productionCategory) {
        return productionCategoryService.updateProductionCategory (productionCategory);
    }

    @RequestMapping(path = "/find",method = RequestMethod.POST)
    @ApiOperation (value = "查询分组信息",notes = "根据id，分组名称来进行分页查询")
    @ResponseBody
    public Object findProductionCategory(ProductionCategory productionCategory) {
        //        ProductionCategory productionCategory = new ProductionCategory ();
        //        ProductionCategory productionCategory1 = new ProductionCategory (null,"2","3",5L,6L);
        //        ProductionCategory productionCategory2 = new ProductionCategory (1L,"2","3",5L,6L);
        //        List<ProductionCategory> list = Arrays.asList (new ProductionCategory[]{productionCategory1, productionCategory2});
        //        productionCategoryService.saveOrUpdateBatch (list,2);
        return productionCategoryService.findProductionCategory (productionCategory);
    }

    @RequestMapping(path = "/findOne",method = RequestMethod.POST)
    @ApiOperation (value = "根据id查询作品分组信息",notes = "根据id，而不是学号，来查询其他信息")
    @ResponseBody
    public ProductionCategory findOneProductionCategory(@RequestBody Long id){
        return productionCategoryService.findByonid (id);
    }

    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "根据id删除作品分组信息",notes = "根据id，而不是学号，来删除信息")
    public String deleteProductionCategory(Long id){
        ProductionScoreCategory productionScoreCategory = new ProductionScoreCategory();
        productionScoreCategory.setProductionCategoryId(id);
        List<ProductionScoreCategory> list = productionScoreCategoryService.findProductionScoreCategory(productionScoreCategory);
        list.forEach(e -> productionScoreCategoryService.deleteProductionScoreCategory (e.getId()));
        productionCategoryService.deleteProductionCategory(id);
        return "success";
    }

    @RequestMapping(path = "/insertOne",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "添加作品分组信息",notes = "根据id，而不是学号，来删除信息")
    public String insertProductionCategory(@RequestBody ProductionCategory productionCategory){
        return productionCategoryService.insertProductionCategory (productionCategory);
    }

}
