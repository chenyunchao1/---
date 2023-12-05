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
@TableName("bs_student")
public class Student{

    @TableId(value = "id",type = IdType.AUTO )
    private Long id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "password")
    private String password="123456";

    @TableField(value = "class_id")
    private Long classId;

    @TableField(value = "stu_nio")
    private Long stuNio;

}
