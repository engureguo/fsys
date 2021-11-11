package com.engure.fsys.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_new")
public class New {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private Date createAt;

}
