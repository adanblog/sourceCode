package com.shan.imgCloud.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shan.imgCloud.entity.SysConfig;
import com.shan.imgCloud.repository.SysConfigRepository;
import com.shan.imgCloud.service.SysConfigService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author adan
 * @since 2019-11-25
 */
@Service
public class SysConfigServiceImpl  implements SysConfigService {
	@Autowired
	SysConfigRepository sysConfigRepository;
	
	public SysConfig save(SysConfig data) {
		return sysConfigRepository.save(data);
	}
	
	public List<SysConfig> findList(){
		return sysConfigRepository.findAll();
	}

	@Override
	@Transactional
	public boolean saveList(List<SysConfig> list) {
		int i=0;
		for (SysConfig data:list) {
			sysConfigRepository.save(data);
			i++;
			if(i==2) {
				//throw new CheckException("9999","抛出异常");
			}
		}
		return true;
	}

	@Override
	public SysConfig getByParaKey(String key) {
		return sysConfigRepository.getByParaKey(key);
	}

	@Override
	public Page<SysConfig> findPage(Pageable p) {
		return sysConfigRepository.findAll(p);
	}
}
