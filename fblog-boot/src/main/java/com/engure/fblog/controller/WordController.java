package com.engure.fblog.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.engure.fblog.entity.User;
import com.engure.fblog.entity.Word;
import com.engure.fblog.exception.GlobalException;
import com.engure.fblog.mapper.WordMapper;
import com.engure.fblog.vo.RespConstants;
import com.engure.fblog.vo.RespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/word")
public class WordController {

    @Autowired
    private WordMapper wordMapper;

    @GetMapping
    public RespVO find(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(defaultValue = "") String search) {

        Page<Word> page = wordMapper.selectPage(new Page<>(pageNum, pageSize),
                Wrappers.<Word>lambdaQuery().like(Word::getName, search));

        return RespVO.success(page);
    }

    @PostMapping
    public RespVO add(@RequestBody Word word) {

        try {

            int modify = wordMapper.insert(word);

            return modify == 1 ? RespVO.success("添加成功") : RespVO.error(RespConstants.FAIL_TO_ADD_WORD);

        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(RespConstants.FAIL_TO_ADD_WORD);
        }

    }

    @PutMapping
    public RespVO update(@RequestBody Word word) {

        int modify = wordMapper.updateById(word);

        return modify == 1 ? RespVO.success("修改成功") : RespVO.error(RespConstants.FAIL_TO_UPDATE_WORD);
    }

    @DeleteMapping("/{id}")
    public RespVO deleteById(@PathVariable(value = "id") Long id) {

        int modify = wordMapper.deleteById(id);

        return modify == 1 ? RespVO.success("删除成功") : RespVO.error(RespConstants.FAIL_TO_DELETE_WORD);
    }

}
