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
@TableName("bs_stu_team")
public class StudentTeam {

    @TableId(value = "id",type = IdType.AUTO )
    private Long id;

    @TableField(value = "student_id")
    private Long studentId;  //学生id

    @TableField(value = "team_id")
    private Long teamId;  //队伍id

    @TableField(value = "team_name")
    private String teamName;  //队伍id

    @TableField(value = "student_name")
    private String studentName;  //队伍id

}
