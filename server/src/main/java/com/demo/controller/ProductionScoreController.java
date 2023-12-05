package com.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.demo.pojo.*;
import com.demo.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@Controller
@RequestMapping(value = "/productionScore")
@Api(value = "作品评分信息管理")
public class ProductionScoreController {

    @Autowired
    private ProductionScoreService productionScoreService;

    @Autowired
    private ProductionScoreCategoryService productionScoreCategoryService;

    @Autowired
    private ProductionService productionService;

    @Autowired
    private ScoreCategoryService scoreCategoryService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/sort",method = RequestMethod.POST)
    @ApiOperation (value = "作品评分排序",notes = "根据作品不同类别进行评分排序")
    @ResponseBody
    public Object sortProduction(Long categoryId) {
        Production production = new Production();
        production.setCategoryId(categoryId);
        List<Production> productionList = productionService.findByProduction(production);
        //  根据作品类别查询到的全部作品
        ProductionScoreCategory productionScoreCategory = new ProductionScoreCategory();
        productionScoreCategory.setProductionCategoryId(categoryId);
        List<ProductionScoreCategory> productionScoreCategoryList = productionScoreCategoryService.findProductionScoreCategory(productionScoreCategory);
        //      根据作品类别查询到的全部评分类别
        List<Long> longList = productionScoreCategoryList.stream().map(ProductionScoreCategory::getScoreCategoryId).collect(Collectors.toList());
        ProductionScore productionScore = new ProductionScore();
        List<ProductionScore> list = productionScoreService.findProductionScore(productionScore);
        //      全部评分信息
//        Map<Long, Map<Long, Long>> collect = list.stream()
//                .collect(Collectors.groupingBy(ProductionScore:: getProductionId,
//                        Collectors.groupingBy(ProductionScore::getScoreTypeId,
//                                Collectors.summingLong(ProductionScore::getScore))));
        List<ProductionScore> re = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < productionList.size(); j++) {
                if(list.get(i).getProductionId().equals(productionList.get(j).getId())){
                    re.add(list.get(i));
                }
            }
        }

        Map<Long, Map<Long, Double>> collect = re.stream()
                .collect(Collectors.groupingBy(ProductionScore:: getProductionId,
                        Collectors.groupingBy(ProductionScore::getScoreTypeId,
                                Collectors.averagingDouble(ProductionScore::getScore))));
//        Map<String,List<Double>> re = new HashMap<>();
//
//        for(Long s:collect.keySet()){
//            for(Long m:collect.get(s).keySet())
//            re.put(scoreCategoryService.findByonid(m).getScoreName(), new ArrayList<>());
//
//        }
//        collect.forEach(e -> collect.get(e).forEach(a -> re.put(scoreCategoryService.findByonid(a).getScoreName(), new ArrayList<>())));
//        collect.size();     //多少条数据
//        List<>

        return collect;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation (value = "更新作品评分信息",notes = "根据id,来修改其他信息")
    @ResponseBody
    public String updateProductionScore(ProductionScore productionScore) {
        return productionScoreService.updateProductionScore (productionScore);
    }

    @RequestMapping(path = "/find",method = RequestMethod.POST)
    @ApiOperation (value = "查询作品评分信息",notes = "根据作品类的属性进行查询")
    @ResponseBody
    public Object findProductionScore(@RequestParam Long productId ) {
        ProductionScore productionScore = new ProductionScore();
        productionScore.setProductionId(productId);
        List<ProductionScore> list = productionScoreService.findProductionScore (productionScore);
        List<ProductionScore> stulist = list.stream().filter(e -> e.getPersonType() == 1).collect(Collectors.toList());
//        .forEach(e -> e.setPersonName(studentService.findByonid(e.getPersonId()).getName())).
        List<ProductionScore> tealist = list.stream().filter(e -> e.getPersonType() == 2).collect(Collectors.toList());
        stulist.forEach(e -> e.setPersonName(studentService.findByonid(e.getPersonId()).getName()));
        tealist.forEach(e -> e.setPersonName(teacherService.findByonid(e.getPersonId()).getName()));
        stulist.addAll(tealist);
        return stulist;
    }

    @RequestMapping(path = "/findOne",method = RequestMethod.POST)
    @ApiOperation (value = "根据id查询作品评分信息",notes = "根据id，即查询单条评论")
    @ResponseBody
    public ProductionScore findOneProductionScore(@RequestBody Long id){
        return productionScoreService.findByonid (id);
    }

    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "根据id删除作品评分信息",notes = "根据id，来删除信息")
    public String deleteProductionScore(@RequestBody Long id){
        return productionScoreService.deleteProductionScore (id);
    }

    @RequestMapping(path = "/insertOne",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation (value = "添加作品评分信息",notes = "根据id，而不是学号，来删除信息")
    public Object insertScoreCategory(HttpServletRequest req, HttpSession session){
        JSONObject jsonObject = new JSONObject();
        String productionId = req.getParameter("productionId");
        String userId = req.getParameter("userId");
        String type = req.getParameter("type");
        String userType = req.getParameter("userType");
        String score = req.getParameter("score");
        ProductionScore productionScore = new ProductionScore();
        productionScore.setProductionId(Long.parseLong(productionId));
        productionScore.setScoreTypeId(Long.parseLong(type));
        productionScore.setPersonId(Long.parseLong(userId));
        productionScore.setPersonType(Integer.parseUnsignedInt(userType));
        List<ProductionScore> list = productionScoreService.findProductionScore(productionScore);
        if(list.size() > 0){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "评分失败,已评分不能重复对该作品评分");
            return jsonObject;
        }
        productionScore.setPersonDate(new Date());
        productionScore.setScore(Integer.parseUnsignedInt(score));
        String s = productionScoreService.insertProductionScore(productionScore);
        if(s.equals("success")){
            jsonObject.put("code", 1);
            jsonObject.put("msg", "评分成功");
        }else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "评分失败,已评分不能重复对该作品评分");
        }
        return jsonObject;
    }
}
