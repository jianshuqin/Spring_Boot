package com.example.test.mapper;

import com.example.test.model.po.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.UUID;

@Mapper
public interface IUserMapper {

    /**
     * 获取用户信息
     */
    @SelectProvider(type = UserDaoProvider.class, method = "get")
    public List<User> getList(String id, String name, Integer offset, Integer limit);

    /**
     * 统计用户信息
     */
    @SelectProvider(type = UserDaoProvider.class, method = "count")
    public int getCount(String name);

    /**
     * 新增用户信息
     */
    @InsertProvider(type = UserDaoProvider.class, method = "insert")
    public int add(User user);

    /**
     * 更新用户信息
     */
    @UpdateProvider(type = UserDaoProvider.class, method = "update")
    public int edit(User user);

    /**
     * 删除用户信息
     */
    @DeleteProvider(type = UserDaoProvider.class, method = "delete")
    public int remove(String id);


    class UserDaoProvider {
        public String get(String id, String name, Integer offset, Integer limit) {
            String sql = new SQL() {
                {
                    SELECT("id,name,password");
                    FROM("user");
                    WHERE("1 = 1");
                    WHERE(StringUtils.isNotBlank(id) ? "id = #{id}" : "1=1");
                    WHERE(StringUtils.isNotBlank(name) ? "name like concat('%',#{name},'%')" : "1=1");
                    if (limit != null && limit > 0) {
                        LIMIT("#{limit}");
                    }
                    if (offset != null && offset > 0) {
                        if (limit == null || limit <= 0) {
                            LIMIT(Integer.MAX_VALUE);
                        }
                        OFFSET("#{offset}");
                    }
                }
            }.toString();
            return sql;
        }

        public String count(String name) {
            String sql = new SQL() {
                {
                    SELECT("count(id)");
                    FROM("user");
                    WHERE("1 = 1");
                    WHERE(StringUtils.isNotBlank(name) ? "name like concat('%',#{name},'%')" : "1=1");
                }
            }.toString();
            return sql;
        }

        public String insert(User user) {
            String sql = new SQL() {
                {
                    user.setId(UUID.randomUUID().toString());
                    INSERT_INTO("user");
                    VALUES("id", "#{id}");
                    VALUES("name", "#{name}");
                    VALUES("password", "#{password}");
                }
            }.toString();
            return sql;
        }

        public String update(User user) {
            String sql = new SQL() {
                {
                    UPDATE("user");
                    SET("name = #{name}");
                    WHERE("id = #{id}");
                }
            }.toString();
            return sql;
        }

        public String delete(String id) {
            String sql = new SQL() {
                {
                    DELETE_FROM("user");
                    WHERE("id = #{id}");
                }
            }.toString();
            return sql;
        }
    }
}
