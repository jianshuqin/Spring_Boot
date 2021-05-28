package com.example.test.controller;

import com.example.test.model.po.User;
import com.example.test.service.UserService;
import com.example.test.model.dto.JsonResult;
import com.example.test.model.dto.PageList;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
@Api(tags = {"用户接口"})
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation("获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = false),
            @ApiImplicitParam(name = "offset", value = "开始索引", required = false),
            @ApiImplicitParam(name = "limit", value = "限制数", required = false)
    })
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public JsonResult<PageList<User>> getList(@RequestParam(required = false) String name, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
        JsonResult<PageList<User>> jr = userService.getList(name, offset, limit);
        return jr;
    }

    @ApiOperation("获取单个用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true)
    @RequestMapping(method = RequestMethod.GET)
    public JsonResult<User> get(@RequestParam String id) {
        JsonResult<User> jr = userService.get(id);
        return jr;
    }

    @ApiOperation("保存用户信息")
    @ApiImplicitParam(name = "user", value = "用户信息", required = true)
    @RequestMapping(method = RequestMethod.POST)
    public JsonResult save(@RequestBody User user) {
        JsonResult jr = userService.save(user);
        return jr;
    }

    @ApiOperation("删除用户信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true)
    @RequestMapping(method = RequestMethod.DELETE)
    public JsonResult delete(@RequestParam String id) {
        JsonResult jr = userService.remove(id);
        return jr;
    }
}
