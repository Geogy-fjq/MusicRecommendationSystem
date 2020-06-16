package com.horizon.common.quartz.task;

import org.quartz.JobExecutionContext;

public interface BatchMgr {

	/**
	 * 执行跑批
	 */

	public  void invoke(JobExecutionContext context);

}
