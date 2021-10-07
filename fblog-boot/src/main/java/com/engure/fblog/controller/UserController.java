package com.engure.fblog.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.engure.fblog.entity.User;
import com.engure.fblog.exception.GlobalException;
import com.engure.fblog.mapper.UserMapper;
import com.engure.fblog.vo.RespConstants;
import com.engure.fblog.vo.RespVO;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询
     *
     * @param pageNum  页码
     * @param pageSize 大小
     * @param search   查询关键词
     */
    @GetMapping
    public RespVO find(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(defaultValue = "") String search) {

        // System.out.println(pageNum + "," + pageSize + "," + keywords);

        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize),
                Wrappers.<User>lambdaQuery().like(User::getUserName, search));

        return RespVO.success(userPage);
    }

    /**
     * 添加用户
     *
     * @param user 注意使用 @RequestBody 接收post请求参数
     */
    @PostMapping
    public RespVO add(@RequestBody User user) {

        try {

            if (user.getPassword() == null) {
                user.setPassword("123");
            }

            int modify = userMapper.insert(user);

            return modify == 1 ? RespVO.success("添加成功") : RespVO.error(RespConstants.FAIL_TO_ADD_USER);

        } catch (Exception e) {
            // e.printStackTrace();
            throw new GlobalException(RespConstants.FAIL_TO_ADD_USER);
        }

    }

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     */
    @PutMapping
    public RespVO update(@RequestBody User user) {

        int modify = userMapper.updateById(user);

        return modify == 1 ? RespVO.success("修改成功") : RespVO.error(RespConstants.FAIL_TO_UPDATE_USER);
    }

    /**
     * 删除用户信息
     *
     * @param id 用户id
     */
    @DeleteMapping("/{id}")
    public RespVO deleteById(@PathVariable(value = "id") Long id) {

        int modify = userMapper.deleteById(id);

        return modify == 1 ? RespVO.success("删除成功") : RespVO.error(RespConstants.FAIL_TO_DELETE_USER);
    }

    /**
     * 登陆校验
     *
     * @param user 前端传来的用户信息凭证
     */
    @PostMapping("/login")
    public RespVO login(@RequestBody User user) {

        try {

            // username 唯一，插入数据时需要做校验

            User u = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                    .eq(User::getUserName, user.getUserName())
                    .eq(User::getPassword, user.getPassword()));

            return u == null ? RespVO.error(RespConstants.LOGIN_ERROR) : RespVO.success(u);

        } catch (Exception e) {
            // e.printStackTrace();
            throw new GlobalException(RespConstants.LOGIN_ERROR);
        }

    }

    @PostMapping("/register")
    public RespVO register(@RequestBody User user) {

        try {

            Page<User> userPage = userMapper.selectPage(new Page<>(1, 10), Wrappers.<User>lambdaQuery()
                    .eq(User::getUserName, user.getUserName()));

            long total = userPage.getTotal();

            if (total > 0) return RespVO.error(RespConstants.REGISTER_USERNAME_EXISTS);

            if (user.getPassword() == null) {
                user.setPassword("123");
            }

            int modify = userMapper.insert(user);

            if (modify == 1) return RespVO.success("注册成功！");
            return RespVO.error(RespConstants.REGISTER_ERROR, "注册失败！");

        } catch (Exception e) {
            // e.printStackTrace();
            return RespVO.error(RespConstants.REGISTER_ERROR, e.getMessage());
        }

    }

}
