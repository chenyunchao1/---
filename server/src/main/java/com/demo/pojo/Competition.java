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
@TableName("bs_competition")
//赛事信息表
public class Competition {

    @TableId(value = "id",type = IdType.AUTO )
    private Long id;

    @TableField(value = "competition_name")
    private String competitionName;  //比赛名称

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM- dd")
    @DateTimeFormat(pattern = "yyyy-MM- dd")
    @TableField(value = "competition_creat_time")
    private Date competitionCreatTime;  //发布时间

    @TableField(value = "competition_creat_id")
    private Long competitionCreatId;   //创建者id

    @TableField(value = "competition_title")
    private String competitionTitle;    //比赛主题

    @TableField(value = "competition_join_end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date competitionJoinEndTime;   //报名截止时间

    @TableField(value = "competition_start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")


    private Date competitionStartTime;    //比赛时间

    @TableField(value = "competition_url")
    private String competitionUrl;
}
