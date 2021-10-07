package com.engure.fblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_word")
public class Word {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String des;

    @JsonFormat(pattern = "yyyy-MM-dd") // 定义格式或配置 spring.jackson.date-format
    private Date createAt;
}
