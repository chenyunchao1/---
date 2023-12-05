package com.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bs_score_category")
public class ScoreCategory {

    @TableId(value = "id",type = IdType.AUTO )
    private Long id;

    @TableField(value = "score_name")
    private String scoreName;  //评分类别

    @TableField(value = "score_sys")
    private Integer scoreSys;  //评分制度
}
