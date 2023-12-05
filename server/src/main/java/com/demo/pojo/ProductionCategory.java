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
@TableName("bs_production_category")
public class ProductionCategory {

    @TableId(value = "id",type = IdType.AUTO )
    private Long id;


    @TableField(value = "category_name")
    private String categoryName;  //作品类别名称


}
