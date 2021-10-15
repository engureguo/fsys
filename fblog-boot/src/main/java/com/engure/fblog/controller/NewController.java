package com.engure.fblog.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.engure.fblog.entity.New;
import com.engure.fblog.exception.GlobalException;
import com.engure.fblog.mapper.NewMapper;
import com.engure.fblog.vo.RespConstants;
import com.engure.fblog.vo.RespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 头像操作
 */

@RestController
@RequestMapping("/news")
public class NewController {

    @Autowired
    private NewMapper newMapper;

    @GetMapping
    public RespVO find(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(defaultValue = "") String search) {

        Page<New> page = newMapper.selectPage(new Page<>(pageNum, pageSize),
                Wrappers.<New>lambdaQuery().like(New::getTitle, search));

        return RespVO.success(page);
    }

    @PostMapping
    public RespVO add(@RequestBody New entity) {

        if (entity.getTitle() == null) entity.setTitle("【 无标题 】");

        try {

            int modify = newMapper.insert(entity);

            return modify == 1 ? RespVO.success("添加成功") : RespVO.error(RespConstants.FAIL_TO_ADD_NEW);

        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(RespConstants.FAIL_TO_ADD_NEW);
        }

    }

    @PutMapping
    public RespVO update(@RequestBody New entity) {

        int modify = newMapper.updateById(entity);

        return modify == 1 ? RespVO.success("修改成功") : RespVO.error(RespConstants.FAIL_TO_UPDATE_NEW);
    }

    @DeleteMapping("/{id}")
    public RespVO deleteById(@PathVariable(value = "id") Long id) {

        int modify = newMapper.deleteById(id);

        return modify == 1 ? RespVO.success("删除成功") : RespVO.error(RespConstants.FAIL_TO_DELETE_NEW);
    }

}
