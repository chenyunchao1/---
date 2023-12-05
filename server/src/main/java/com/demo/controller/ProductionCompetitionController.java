package com.demo.controller;



import com.demo.pojo.ProductionCompetition;
import com.demo.service.ProductionCompetitionService;
import com.demo.service.ProductionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping(value = "/productionCompetition")
@Api(value = "作品赛事信息管理")
public class ProductionCompetitionController {

    @Autowired
    private ProductionCompetitionService productionCompetitionService;

    @Autowired
    private ProductionService productionService;

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation (value = "更新作品赛事信息",notes = "根据id,来修改其他信息")
    @ResponseBody
    public String updateProductionCompetition(@RequestBody ProductionCompetition productionCompetition) {
        return productionCompetitionService.updateProductionCompetition (productionCompetition);
    }

    @RequestMapping(path = "/find",method = RequestMethod.POST)
    @ApiOperation (value = "查询赛事报名信息",notes = "根据作品id或赛事id来进行分页查询")
    @ResponseBody
    public Object findPageProductionCompetition(ProductionCompetition productionCompetition) {
        List<ProductionCompetition> list = productionCompetitionService.findProductionCompetition(productionCompetition);
        list.forEach(e -> e.setProductionName(productionService.findByOnid(e.getProductionId()).getProductionName()));
        return list;
    }

    @RequestMapping(path = "/findOne",method = RequestMethod.POST)
    @ApiOperation (value = "根据id查询作品赛事信息",notes = "根据id，来查询其他信息")
    @ResponseBody
    public ProductionCompetition findOneProductionCompetition(Long id){
        return productionCompetitionService.findByonid (id);
    }

    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "根据id删除作品赛事信息",notes = "根据id，来删除信息")
    public String deleteProductionCompetition(Long id){
        return productionCompetitionService.deleteProductionCompetition (id);
    }

    @RequestMapping(path = "/insertOne",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "添加作品赛事信息",notes = "根据id，而不是学号，来删除信息")
    public String insertProductionCompetition(HttpServletRequest req, ProductionCompetition productionCompetition){
        return productionCompetitionService.insertProductionCompetition (productionCompetition);
    }

}
