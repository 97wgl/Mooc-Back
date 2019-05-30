package com.xgxfd.moocback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 讲师编号
     */
    @TableId(value = "tea_id", type = IdType.AUTO)
    private Integer teaId;

    /**
     * 名字
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 手机号
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 工作单位
     */
    private String orgnization;

    /**
     * 职位
     */
    private String position;

    /**
     * 介绍
     */
    private String remark;

    /**
     * 教师资格审核状态：1-审核通过，2-未通过
     */
    private String status;

    /**
     * 教师资格审核材料
     */
    private String applicationMaterial;

}
