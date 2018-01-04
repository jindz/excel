package com.jindz.excel.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.RandomStringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 流水号生成
 * 
 * SerialNumber.java</br>
 * 
 * @author jindz</br>
 * @date 2017年11月24日 上午11:58:19</br>
 *
 */
public class SerialNumber {
	/**
	 * 生成业务流水号 系统标识（sysFlg.length位）+时间（14位，年月日时分秒）+系统流水（randomCount位）
	 * 
	 * @param sysFlg
	 *            系统标识
	 * @param randomCount
	 *            随机数位数
	 * @return
	 */
	public static synchronized String createSerial(String sysFlg,
			int randomCount) {
		safeSleep(1);
		SimpleDateFormat sdft = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date nowdate = new Date();
		String str = sdft.format(nowdate);
		return sysFlg + str + RandomStringUtils.randomNumeric(randomCount);
	}

	public static synchronized String createSerial(int randomCount) {
		safeSleep(1);
		SimpleDateFormat sdft = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date nowdate = new Date();
		String str = sdft.format(nowdate);
		return str + RandomStringUtils.randomNumeric(randomCount);
	}

	/**
	 * 生成9位流水号
	 * 
	 * @author jindz
	 * @date 2017年11月24日 下午1:24:16
	 * @return
	 */
	public static synchronized Integer createSerial() {
		safeSleep(1);
		SimpleDateFormat sdft = new SimpleDateFormat("HHmmssSSS");
		Date nowdate = new Date();
		return Integer.valueOf(sdft.format(nowdate));
	}

	private static void safeSleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
