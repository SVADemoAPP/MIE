package com.bis.dao;

import org.apache.ibatis.annotations.Param;

public interface CpuDao {
	
	public int saveCpu(@Param("cpuParameter") int cpuParameter,@Param("lastTime") long lastTime);
	
	public long selectTimeById();
	
	public int selectParameter();
	
	public int selectCount();

	public int deleteCount(@Param("id") long id);
	
	public long selectMinId();
	
}
