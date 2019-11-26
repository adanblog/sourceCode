package com.shan.imgCloud.entity.primarykey;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * <pre>
 * TODO 自定义主键
 * </pre>
 * @author shanguogang
 * @version 1.0, 2019年11月26日
 */
public class SnowflakeKey implements IdentifierGenerator{
	
	public SnowflakeKey() {
    }
	
	@Override
	public Serializable generate(SharedSessionContractImplementor arg0, Object arg1) throws HibernateException {
		IdWorker id=new IdWorker();
		return String.valueOf(id.getNextId());
	}

}
