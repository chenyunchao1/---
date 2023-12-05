package com.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bs_production")
public class Production {

    @TableId(value = "id",type = IdType.AUTO )
    private Long id;

    @TableField(value = "production_name")
    private String productionName;  //作品名称

    @TableField(value = "production_title_picture")
    private String productionTitlePicture;  //作品封面

    @TableField(value = "category_id")
    private Long categoryId;  //作品类别id

    @TableField(value = "team_id")
    private Long teamId;   //队伍id

    @TableField(value = "team_name")
    private String teamName;    //队伍名字

    @TableField(value = "team_type")
    private Integer teamType;    //队伍类型 0学生 1 老师

    @TableField(value = "creat_time")
    private Date creatTime;       //作品创建时间

    @TableField(value = "creat_person_id")
    private Long creatPersonId;     //创建人id

    @TableField(value = "creat_person_type")
    private Integer creatPersonType;     //创建人类别

    @TableField(value = "update_time")
    private Date updateTime;        //修改时间

    @TableField(value = "update_person_id")
    private Long updatePersonId;    //修改人id

    @TableField(value = "update_person_type")
    private Long updatePersonType;     //修改人类别

    @TableField(value = "production_teacher_id")
    private Long productionTeacherId;   //作品指导老师id

    @TableField(value = "production_text")
    private String productionText;      //作品内容说明

    @TableField(value = "production_video")
    private String productionVideo;     //作品视频

    @TableField(value = "production_document")
    private String productionDocument;  //作品文档

}
