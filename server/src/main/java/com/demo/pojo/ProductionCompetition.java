package com.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bs_production_competition")
//作品-赛事表
public class ProductionCompetition {

    @TableId(value = "id",type = IdType.AUTO )
    private Long id;

    @TableField(value = "production_id")
    private Long productionId;  //作品id

    @TableField(value = "competition_id")
    private Long competitionId;  //赛事id

    @TableField(value = "production_name")
    private String productionName;  //比赛名称

    @TableField(value = "join_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date joinTime;   //参赛时间

    @TableField(value = "join_winning")
    private String joinWinning;  //获奖情况

}
