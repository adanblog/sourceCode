package com.shan.imgCloud.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统配置表
 * </p>
 *
 * @author adan
 * @since 2019-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class SysConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 键
     */
    @Column(nullable = false, unique = true)
    private String paraKey;

    /**
     * 值
     */
    @Column(nullable = false)
    private String paraValue;

    /**
     * 说明
     */
    @Column(nullable = true)
    private String paraDesc;

    /**
     * 配置分组
     */
    @Column(nullable = true)
    private String paramGroup;


}
