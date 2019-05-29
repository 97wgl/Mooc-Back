package com.xgxfd.moocback.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Xxz Wgl
 * @since 2019-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @TableId(value = "u_id", type = IdType.AUTO)
    private Integer uId;

    /**
     * 用户名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像路径
     */
    private String headImg;

    /**
     * 密码:md5加密
     */
    private String pwd;

    /**
     * 注册时间
     */
    private LocalDateTime registDate;

    /**
     * 个人签名
     */
    private String remark;


}
