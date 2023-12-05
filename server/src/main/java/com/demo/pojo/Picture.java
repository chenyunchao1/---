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
@TableName("bs_picture")
public class Picture {

    @TableId(value = "id",type = IdType.AUTO )
    private Long id;

    @TableField(value = "picture_url")
    private String pictureUrl;  //图片路径

    @TableField(value = "creat_time")
    private Date creatTime;  //图片创建时间

    @TableField(value = "production_id")
    private Long productionId;   //关联作品id

}
