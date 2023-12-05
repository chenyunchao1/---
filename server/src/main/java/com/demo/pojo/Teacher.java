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
@TableName("bs_teacher")
public class Teacher {
    @TableId(value = "id",type = IdType.AUTO )
    private Long id;

    @TableField(value = "teacher_name")
    private String name;

    @TableField(value = "teacher_password")
    private String teacherPassword;

    @TableField(value = "teacher_phone")
    private String teacherPhone;


}
