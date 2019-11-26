
package com.shan.imgCloud.entity.primarykey;

import java.math.BigInteger;
import java.util.Date;

/**
 * <pre>
 * TODO SnowFlake算法生成ID主键
 * SnowFlake算法生成id的结果是一个64bit大小的整数
 * 把整个ID分为4个部位，
 * 1、第一部位：第一位，不用。二进制中最高位为1的都是负数，但是我们生成的id一般都使用整数，所以这个最高位固定是0
 * 2、第二部位：用41bit毫秒级的时间，一般实现上不会存储当前的时间戳，而是时间戳的差值（当前时间-固定的开始时间），这样可以使产生的ID从更小值开始；
 * 用41bit时间戳（毫秒）,41位可以表示241−1个数字。
 * 如果只用来表示正整数（计算机中正数包含0），可以表示的数值范围是：0 至 241−1，减1是因为可表示的数值范围是从0开始算的，而不是1。
 * 也就是说41位可以表示241−1个毫秒的值，转化成单位年则是(241−1)/(1000∗60∗60∗24∗365)=69年
 * 41位的时间戳可以使用69年，(1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69年；
 * 3、第三部位：5bit工作机器序号+5bit数据中心序号，一共10bit。可以部署在210=1024个节点，包括5位datacenterId和5位workerId
 *   5位（bit）可以表示的最大正整数是25−1=31，即可以用0、1、2、3、....31这32个数字，来表示不同的datecenterId或workerId
 * 4、第四部位：12bit自增序列号。用来记录同毫秒内产生的不同id。12位（bit）可以表示的最大正整数是212−1=4095，
 * 即可以用0、1、2、3、....4094这4095个数字，来表示同一机器同一时间截（毫秒)内产生的4095个ID序号
 * </pre>
 * 
 * @author shanguogang
 * @version 1.0, 2018年10月10日
 */
public class IdWorker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		IdWorker id = new IdWorker();
		for (int i = 0; i < 1000; i++) {
			System.out.println(id.getNextId());
		}

	}

	/**
	 * 生成id多少位
	 */
	private final static int ID_LENGTH_BIT = 64;
	// 起始的时间戳
	private final static long START_STMP = 1539182212701L;
	// 机器标识占用的位数
	private final static long WORKER_ID_BIT = 5L;
	// 机械标识最大数
	private final static long MAX_WORKER_ID_BIT = -1L ^ (-1L << WORKER_ID_BIT);
	// 数据中心占用的位数
	private final static long DATACENTER_ID_BIT = 5L;
	// 数据中心最大数
	private final static long MAX_DATACENTER_ID_BIT = -1L ^ (-1L << DATACENTER_ID_BIT);
	// 序列号占用的位数
	private final static long SEQUENCE_BIT = 12L;
	private final static long MAX_SEQUENCE_BIT = -1L ^ (-1L << SEQUENCE_BIT);

	// 每一部分向左的位移
	private final static long WORKER_LEFT = SEQUENCE_BIT;
	private final static long DATACENTER_LEFT = SEQUENCE_BIT + WORKER_ID_BIT;
	private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_ID_BIT;

	private long datacenterId;// 数据中心
	private long workerId;// 机器标识
	private long sequence = 0L; // 序列号
	private long lastStmp = -1L;// 上一次时间戳

	/**
	 * 构造函数
	 * 
	 */
	public IdWorker() {
		this.datacenterId = 1;
		this.workerId = 1;
		if (datacenterId > MAX_DATACENTER_ID_BIT || datacenterId < 0) {
			throw new IllegalArgumentException("数据中心超出最大" + MAX_DATACENTER_ID_BIT + "或者小于0");
		}
		if (workerId > MAX_WORKER_ID_BIT || workerId < 0) {
			throw new IllegalArgumentException("机械标识超出最大" + MAX_WORKER_ID_BIT + "或者小于0");
		}
		
	}
	
	/**
	 * 构造函数
	 * 
	 * @param datacenterId
	 *            数据中心
	 * @param workerId
	 *            机器标识
	 */
	public IdWorker(long datacenterId, long workerId) {
		if (datacenterId > MAX_DATACENTER_ID_BIT || datacenterId < 0) {
			throw new IllegalArgumentException("数据中心超出最大" + MAX_DATACENTER_ID_BIT + "或者小于0");
		}
		if (workerId > MAX_WORKER_ID_BIT || workerId < 0) {
			throw new IllegalArgumentException("机械标识超出最大" + MAX_WORKER_ID_BIT + "或者小于0");
		}
		this.datacenterId = datacenterId;
		this.workerId = workerId;
	}
	
	/**
	 * <pre>
	 * 获取下一个ID
	 * </pre>
	 * 
	 * @return
	 */
	public synchronized long getNextId() {
		// 当前时间戳
		long currMill = getNewStmp();
		if (currMill < lastStmp) {
			throw new IllegalArgumentException("时间异常，拒绝生成ID");
		}
		if (currMill == lastStmp) {
			// 当前时间戳与上一个时间戳相同(在同一毫秒内)，只能通过序列号自增来保证唯一
			sequence = (sequence + 1) & MAX_SEQUENCE_BIT;
			if (sequence == 0L) {
				// 同一毫秒的序列数已经达到最大，只能等待下一个毫秒
				currMill = getNextMill();
			}
		} else {
			// 不同毫秒内，把序列号置为0
			sequence = 0L;
		}
		// 把当前时间戳，重置到上一个时间戳
		lastStmp = currMill;
		// 就是用相对毫秒数、机器ID和自增序号拼接
		return (currMill - START_STMP) << TIMESTMP_LEFT // 时间戳部分
				| datacenterId << DATACENTER_LEFT // 数据中心部分
				| workerId << WORKER_LEFT // 机器标识部分
				| sequence; // 序列号部分
	}

	/**
	 * <pre>
	 * 获取下一个时间戳
	 * </pre>
	 * 
	 * @return
	 */
	private long getNextMill() {
		long mill = getNewStmp();
		while (mill <= lastStmp) {
			mill = getNewStmp();
		}
		return mill;
	}

	/**
	 * <pre>
	 * 获取当前时间戳
	 * </pre>
	 * 
	 * @return
	 */
	private long getNewStmp() {
		return System.currentTimeMillis();
	}

	/**
	 * <pre>
	 * 获取ID的创建时间
	 * </pre>
	 * 
	 * @return
	 */
	public static Date getIDCreateTime(Long id) {
		String ejz = toBinaryString(id);
		String sj = ejz.substring(1, 42);
		BigInteger bi = new BigInteger(sj, 2);
		return new Date(Long.parseLong(bi.toString()) + START_STMP);
	}

	/**
	 * <pre>
	 * 获取工作机器编号
	 * </pre>
	 * 
	 * @return
	 */
	public static Long getWorkerId(Long id) {
		String ejz = toBinaryString(id);
		String sj = ejz.substring(42, 47);
		BigInteger bi = new BigInteger(sj, 2);
		return Long.parseLong(bi.toString());
	}

	/**
	 * <pre>
	 * 获取数据中心编号
	 * </pre>
	 * 
	 * @return
	 */
	public static Long getDatacenterId(Long id) {
		String ejz = toBinaryString(id);
		String sj = ejz.substring(47, 52);
		BigInteger bi = new BigInteger(sj, 2);
		return Long.parseLong(bi.toString());
	}

	/**
	 * <pre>
	 * 获取自增序号
	 * </pre>
	 * 
	 * @return
	 */
	public static Long getSequence(Long id) {
		String ejz = toBinaryString(id);
		String sj = ejz.substring(52);
		BigInteger bi = new BigInteger(sj, 2);
		return Long.parseLong(bi.toString());
	}

	/**
	 * <pre>
	 * 把ID转成二进制
	 * </pre>
	 * 
	 * @param id
	 * @return
	 */
	public static String toBinaryString(Long id) {
		String binary = Long.toBinaryString(id);
		StringBuffer bu = new StringBuffer();
		for (int i = 0; i < (ID_LENGTH_BIT - binary.length()); i++) {
			bu.append("0");
		}
		return bu + binary;
	}
}
