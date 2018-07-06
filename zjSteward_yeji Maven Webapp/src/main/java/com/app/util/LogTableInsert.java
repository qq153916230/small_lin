package com.app.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.dao.AidgoldLogDao;
import com.app.entity.AidgoldLog;
/**
 * 返回一个助理金日志对象
 * */
public class LogTableInsert {
	
	private static AidgoldLog aidgoldLog;
	
	/**
	 * 返回一个助理金日志对象
	 * @mid 记录操作人员账号
	 * @stype 记录类型
	 * @cont 记录内容
	 * */
	public static AidgoldLog insert(String mid, String stype, String cont){
		if (aidgoldLog == null) {
			aidgoldLog = new AidgoldLog();
		}
		aidgoldLog.setMid(mid);
		aidgoldLog.setStype(stype);
		aidgoldLog.setCont(cont);
		return aidgoldLog;
	}

}
