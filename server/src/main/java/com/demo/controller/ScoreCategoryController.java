package com.demo.controller;


import com.demo.pojo.ProductionScoreCategory;
import com.demo.pojo.ScoreCategory;
import com.demo.service.ProductionScoreCategoryService;
import com.demo.service.ScoreCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@Controller
@RequestMapping(value = "/scoreCategory")
@Api(value = "作品评分信息管理")
public class ScoreCategoryController {

    @Autowired
    private ScoreCategoryService scoreCategoryService;

    @Autowired
    private ProductionScoreCategoryService productionScoreCategoryService;


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation (value = "更新作品评分信息",notes = "根据id,来修改其他信息")
    @ResponseBody
    public String updateScoreCategory(ScoreCategory scoreCategory) {
        return scoreCategoryService.updateScoreCategory (scoreCategory);
    }

    @RequestMapping(path = "/find",method = RequestMethod.POST)
    @ApiOperation (value = "查询用户信息",notes = "根据姓名、班级、学号来进行分页查询")
    @ResponseBody
    public List<ScoreCategory> findPageScoreCategory(ScoreCategory scoreCategory ) {
        //        ScoreCategory scoreCategory = new ScoreCategory ();
        //        ScoreCategory scoreCategory1 = new ScoreCategory (null,"2","3",5L,6L);
        //        ScoreCategory scoreCategory2 = new ScoreCategory (1L,"2","3",5L,6L);
        //        List<ScoreCategory> list = Arrays.asList (new ScoreCategory[]{scoreCategory1, scoreCategory2});
        //        scoreCategoryService.saveOrUpdateBatch (list,2);
        return scoreCategoryService.findScoreCategory (scoreCategory);
    }

    @RequestMapping(path = "/findOne",method = RequestMethod.POST)
    @ApiOperation (value = "根据id查询作品评分信息",notes = "根据id，而不是学号，来查询其他信息")
    @ResponseBody
    public ScoreCategory findOneScoreCategory(@RequestBody Long id){
        return scoreCategoryService.findByonid (id);
    }

    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "根据id删除作品评分信息",notes = "根据id，而不是学号，来删除信息")
    public String deleteScoreCategory(Long id){
        ProductionScoreCategory productionScoreCategory = new ProductionScoreCategory();
        productionScoreCategory.setScoreCategoryId(id);
        List<ProductionScoreCategory> list = productionScoreCategoryService.findProductionScoreCategory(productionScoreCategory);
        list.forEach(e -> productionScoreCategoryService.deleteProductionScoreCategory(e.getId()));
        return scoreCategoryService.deleteScoreCategory (id);
    }

    @RequestMapping(path = "/insertOne",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "添加作品评分信息",notes = "根据id，而不是学号，来删除信息")
    public String insertScoreCategory(ScoreCategory scoreCategory){
        List<ScoreCategory> list = scoreCategoryService.findScoreCategory(scoreCategory);
        if(list.size()>0){
            return "error";
        }
        return scoreCategoryService.insertScoreCategory (scoreCategory);
    }

}
