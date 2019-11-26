package com.shan.imgCloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shan.imgCloud.entity.SysConfig;

public interface SysConfigRepository extends JpaRepository<SysConfig, Long>,PagingAndSortingRepository<SysConfig, Long> {
	/**
	 * <pre>
	 * 根据paraKey获取数据
	 * </pre>
	 * @param key
	 * @return
	 */
	SysConfig getByParaKey(String key);
	
}
