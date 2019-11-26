package com.shan.imgCloud.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shan.imgCloud.entity.SysConfig;
import com.shan.imgCloud.service.SysConfigService;

/**
 * <p>
 * 系统配置 前端控制器
 * </p>
 *
 * @author adan
 * @since 2019-11-25
 */
@RestController
@RequestMapping("/sysConfig")
public class SysConfigController extends BaseController {
	/**
	 * 
	 */
	@Autowired
	public SysConfigService sysConfigService;
	
	@RequestMapping(value = "/save")
	public SysConfig save() {
		SysConfig data=new SysConfig();
		data.setParaKey("test");
		data.setParaValue("112233");
		return sysConfigService.save(data);
	}
	
	@RequestMapping(value = "/saveList")
	public Boolean saveList() {
		List<SysConfig> list=new ArrayList<>();
		for(int i=0;i<10;i++) {
			SysConfig data=new SysConfig();
			data.setParaKey("test"+i);
			data.setParaValue("ttt"+i);
			list.add(data);
		}
		return sysConfigService.saveList(list);
	}
	
	@RequestMapping(value = "/findList")
	public List<SysConfig> findList(){
		return sysConfigService.findList();
	}
	
	@RequestMapping(value = "/getByParaKey")
	public SysConfig getByParaKey(String key) {
		return sysConfigService.getByParaKey(key);
	}

	@RequestMapping(value = "/findPage")
	public Page<SysConfig> findPage(Integer pageIndex) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable page = PageRequest.of(pageIndex - 1, 2, sort);
		return sysConfigService.findPage(page);
	}
}
