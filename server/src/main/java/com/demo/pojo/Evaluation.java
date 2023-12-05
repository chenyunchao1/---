package com.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.org.apache.bcel.internal.generic.DDIV;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bs_evaluation")
public class Evaluation {

    @TableId(value = "id",type = IdType.AUTO )
    private Long id;

    @TableField(value = "evaluation_production_id")
    private Long evaluationProductionId;  //评价作品id

    @TableField(value = "evaluation_person_id")
    private Long evaluationPersonId;  //评价用户id

    @TableField(value = "evaluation_person_name")
    private String evaluationPersonName;   //评价用户姓名

    @TableField(value = "evaluation_person_type")
    private Integer evaluationPersonType;  //评价用户类别

    @TableField(value = "evaluation_time")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM- dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM- dd HH:mm:ss")
    private Date evaluationTime;  //评价时间

    @TableField(value = "evaluation_text")
    private String evaluationText;   //评价内容
}
