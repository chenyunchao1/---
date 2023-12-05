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
@TableName("bs_production_score_category")
//作品评分-作品类别表
public class ProductionScoreCategory {

    @TableId(value = "id",type = IdType.AUTO )
    private Long id;

    @TableField(value = "score_category_id")
    private Long scoreCategoryId;  //评分类别id

    @TableField(value = "production_category_id")
    private Long productionCategoryId;  //作品类别id

}
