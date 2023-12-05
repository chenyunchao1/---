package com.demo.controller;

import com.demo.pojo.Competition;
import com.demo.pojo.ProductionCompetition;
import com.demo.pojo.ProductionScore;
import com.demo.service.CompetitionService;
import com.demo.service.ProductionCompetitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * @autthor lc1100
 * @create 2022-04-07-14:09
 */

@CrossOrigin
@Controller
@Api(value = "赛事信息信息管理")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private ProductionCompetitionService productionCompetitionService;

    @RequestMapping(path = "/competition/find",method = RequestMethod.POST)
    @ApiOperation(value = "查询赛事信息")
    @ResponseBody
    public Object findCompetition(Competition competition) {
        return competitionService.findCompetition(competition);
    }


    @RequestMapping(path = "/competition/insertOne",method = RequestMethod.POST)
    @ApiOperation(value = "添加赛事信息")
    @ResponseBody
    public Object insertOne(Competition competition) {
        competition.setCompetitionCreatTime(new Date());
        return competitionService.insertCompetition(competition);
    }

    @RequestMapping(path = "/competition/delete",method = RequestMethod.POST)
    @ApiOperation(value = "删除赛事信息")
    @ResponseBody
    public Object deleteOne(Long id) {
        ProductionCompetition productionCompetition = new ProductionCompetition();
        productionCompetition.setCompetitionId(id);
        List<ProductionCompetition> productionCompetitionList = productionCompetitionService.findProductionCompetition(productionCompetition);
        if(productionCompetitionList.size()!=0)
            productionCompetitionList.forEach(e -> productionCompetitionService.deleteProductionCompetition(e.getId()));
        return competitionService.deleteCompetition(id);
    }

    @RequestMapping(path = "/competition/update",method = RequestMethod.POST)
    @ApiOperation(value = "删除赛事信息")
    @ResponseBody
    public Object updateOne(Competition competition) {
        return competitionService.updateCompetition(competition);
    }
}
