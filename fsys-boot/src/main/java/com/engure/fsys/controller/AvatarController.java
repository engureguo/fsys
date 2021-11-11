package com.engure.fsys.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.engure.fsys.entity.Avatar;
import com.engure.fsys.exception.GlobalException;
import com.engure.fsys.mapper.AvatarMapper;
import com.engure.fsys.vo.RespConstants;
import com.engure.fsys.vo.RespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 头像操作
 */

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    @Autowired
    private AvatarMapper avatarMapper;

    @GetMapping
    public RespVO find(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(defaultValue = "") String search) {

        Page<Avatar> page = avatarMapper.selectPage(new Page<>(pageNum, pageSize),
                Wrappers.<Avatar>lambdaQuery().like(Avatar::getName, search));

        return RespVO.success(page);
    }

    @PostMapping
    public RespVO add(@RequestBody Avatar entity) {

        try {

            int modify = avatarMapper.insert(entity);

            return modify == 1 ? RespVO.success("添加成功") : RespVO.error(RespConstants.FAIL_TO_ADD_AVATAR);

        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(RespConstants.FAIL_TO_ADD_AVATAR);
        }

    }

    @PutMapping
    public RespVO update(@RequestBody Avatar entity) {

        int modify = avatarMapper.updateById(entity);

        return modify == 1 ? RespVO.success("修改成功") : RespVO.error(RespConstants.FAIL_TO_UPDATE_AVATAR);
    }

    @DeleteMapping("/{id}")
    public RespVO deleteById(@PathVariable(value = "id") Long id) {

        int modify = avatarMapper.deleteById(id);

        return modify == 1 ? RespVO.success("删除成功") : RespVO.error(RespConstants.FAIL_TO_DELETE_AVATAR);
    }

}
