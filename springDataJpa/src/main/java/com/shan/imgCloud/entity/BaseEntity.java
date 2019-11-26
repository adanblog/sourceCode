package com.shan.imgCloud.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * <pre>
 * TODO 基类
 * </pre>
 * @author shanguogang
 * @version 1.0, 2019年11月26日
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -298421974929033549L;
	@Id
    @GeneratedValue(generator="snowflakeKey")
	@GenericGenerator(name = "snowflakeKey", strategy = "com.shan.imgCloud.entity.primarykey.SnowflakeKey")
    private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
