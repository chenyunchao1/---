package com.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.pojo.Evaluation;
import com.demo.pojo.Student;
import com.demo.pojo.Teacher;
import com.demo.service.EvaluationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @autthor lc1100
 * @create 2022-03-28-18:08
 */
@CrossOrigin
@Controller
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @RequestMapping(value = "/evaluation/findAll")
    @ApiOperation(value = "根据图片所属作品的Id查询作品列表")
    @ResponseBody
    public Object findAllByProductionId(HttpServletRequest req, @RequestParam Long productionId){
        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluationProductionId(productionId);
        List<Evaluation> evaluations =evaluationService.findEvaluation(evaluation);
        return evaluations;
    }

    @RequestMapping(value = "/evaluation/add")
    @ApiOperation(value = "根据图片所属作品的Id查询作品列表")
    @ResponseBody
    public Object evaluationAdd(HttpServletRequest req, HttpSession session){
        JSONObject jsonObject = new JSONObject();
        String productionId = req.getParameter("productionId");
        String userId = req.getParameter("userId");
        String type = req.getParameter("type");
        String userName = req.getParameter("userName");
        String evaluationText = req.getParameter("evaluationText");
        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluationProductionId(Long.parseLong(productionId));
        evaluation.setEvaluationPersonId(Long.parseLong(userId));
        evaluation.setEvaluationPersonType(Integer.parseUnsignedInt(type));
        evaluation.setEvaluationPersonName(userName);
        evaluation.setEvaluationTime(new Date());
        evaluation.setEvaluationText(evaluationText);
        String s = evaluationService.insertEvaluation(evaluation);
        if(s.equals("success")){
            jsonObject.put("code", 1);
            jsonObject.put("msg", "添加成功");
        }else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "添加失败");
        }
        return jsonObject;
    }

}
