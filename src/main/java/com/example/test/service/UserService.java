package com.example.test.service;

import com.example.test.model.dto.JsonResult;
import com.example.test.model.dto.PageList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import com.example.test.mapper.IUserMapper;
import com.example.test.model.po.User;

import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserMapper userMapper;
    @Autowired
    private StringRedisTemplate  stringRedisTemplate ;

    public JsonResult<PageList<User>> getList(String name, Integer offset, Integer limit) {
        JsonResult<PageList<User>> jr = new JsonResult<PageList<User>>();
        PageList<User> pl = new PageList<>();
        try {
            int total = userMapper.getCount(name);
            if (total > 0) {
                pl.setList(userMapper.getList(null, name, offset, limit));
            }
            pl.setTotal(total);
            jr.setData(pl);
            jr.setResult(true);
            jr.setCode(200);
        } catch (Exception ex) {
            jr.setMessage(ex.getMessage());
        }
        ValueOperations<String, String> redis = stringRedisTemplate.opsForValue();
        String aa = redis.set
        return jr;
    }

    public JsonResult<User> get(String id) {
        JsonResult<User> jr = new JsonResult<User>();
        List<User> list = userMapper.getList(id, null, null, null);
        if (list != null && list.size() > 0) {
            jr.setData(list.get(0));
        }
        return jr;
    }

    public JsonResult save(User user) {
        JsonResult jr = new JsonResult();
        if (user != null) {
            String id = user.getId();
            try {
                int count = (StringUtils.isBlank(id) || this.get(id) == null) ? userMapper.add(user) : userMapper.edit(user);
                jr.setResult(true);
            } catch (Exception ex) {
                jr.setMessage(ex.getMessage());
            }
        } else {
            jr.setMessage("参数不能为空!");
        }
        return jr;
    }

    public JsonResult remove(String id) {
        JsonResult jr = new JsonResult();
        if (StringUtils.isNotBlank(id)) {
            try {
                int count = userMapper.remove(id);
                jr.setResult(true);
            } catch (Exception ex) {
                jr.setMessage(ex.getMessage());
            }
        } else {
            jr.setMessage("id不能为空");
        }
        return jr;
    }
}
