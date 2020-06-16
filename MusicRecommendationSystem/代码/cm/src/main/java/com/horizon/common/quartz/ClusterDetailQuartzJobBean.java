package com.horizon.common.quartz;

import java.lang.reflect.Method;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;


public class ClusterDetailQuartzJobBean extends QuartzJobBean {

	protected final Logger logger = LoggerFactory
			.getLogger(ClusterDetailQuartzJobBean.class);

	// 上下文
	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	// 对象
	private String targetObject;

	public void setTargetObject(String targetObject) {
		this.targetObject = targetObject;
	}

	// 方法
	private String targetMethod;

	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.debug("execute [" + targetObject + "] at once >>>>>>");
		try {
			// 从Spring获取对象
			Object otargetObject = applicationContext.getBean(targetObject);
			// 获取方法
			Method m = otargetObject.getClass().getMethod(targetMethod,
					new Class[] {JobExecutionContext.class});
			// 执行
			m.invoke(otargetObject, new Object[] { context });
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new JobExecutionException(e);
		}
	}

}
