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
@TableName("bs_production_score")
public class ProductionScore {

    @TableId(value = "id",type = IdType.AUTO )
    private Long id;

    @TableField(value = "production_id")
    private Long productionId;  //作品id

    @TableField(value = "person_id")
    private Long personId;  //评分者id

    @TableField(value = "person_name")
    private String personName;  //评分者姓名


    @TableField(value = "person_type")
    private Integer personType;  //评分者类别

    @TableField(value = "person_date")
    private Date personDate;  //评论时间

    @TableField(value = "score_type_id")
    private Long scoreTypeId;  //评分类型id

    @TableField(value = "score_type_name")
    private String scoreTypeName;  //评分类型名字

    @TableField(value = "score")
    private Integer score;  //得分

}
