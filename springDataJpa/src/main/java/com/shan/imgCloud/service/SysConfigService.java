package com.shan.imgCloud.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shan.imgCloud.entity.SysConfig;

/**
 * <p>
 * 
 * </p>
 *
 * @author adan
 * @since 2019-11-25
 */
public interface SysConfigService  {
	public SysConfig save(SysConfig data);
	public boolean saveList(List<SysConfig> list);
	public List<SysConfig> findList();
	
	/**
	 * <pre>
	 * 根据paraKey获取数据
	 * </pre>
	 * @param key
	 * @return
	 */
	SysConfig getByParaKey(String key);
	
	/**
	 * <pre>
	 * 分页查询
	 * </pre>
	 * @param p
	 * @return
	 */
	Page<SysConfig> findPage(Pageable p);
}
