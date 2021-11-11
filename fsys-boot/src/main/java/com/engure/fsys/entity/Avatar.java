package com.engure.fsys.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_avatar")
public class Avatar {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;
    private String url;
    private Date createAt;
}
