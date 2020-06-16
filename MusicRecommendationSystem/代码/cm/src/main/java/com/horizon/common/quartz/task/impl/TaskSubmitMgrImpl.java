package com.horizon.common.quartz.task.impl;


import com.horizon.common.util.DateUtil;
import com.horizon.common.util.SysContextUtil;
import com.horizon.music.artist.dao.ArtistDAO;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import com.horizon.common.quartz.task.BatchMgr;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 向yarn 上提交Spark 任务
 */
public class TaskSubmitMgrImpl implements BatchMgr {

	private static Logger log = Logger.getLogger(TaskSubmitMgrImpl.class);

	private static final String shell = "/root/spark/spark-1.6.3-bin-hadoop2.6//bin/spark-submit --master yarn-client  --num-executors 2 --driver-memory 2g --executor-memory 2g --executor-cores 2 ";
	private static final String space = " ";
	//private static final String exeClass = "--class com.horizon.pt.AlsModel  /root/algorithm/practicaltraining_2.10-1.0.jar hdfs://192.168.88.104:8020/out/usrArtist/rattingData/ hdfs://192.168.88.104:8020/out/usrArtist/model/";
	//定义生成log 的根目录
	private String logDir;
	//执行的类
	private String exeClass;
	//执行的jar 包
	private String exeJar;
	//执行任务的相关参数
	private String params;
	@Override
	public void invoke(JobExecutionContext context) {

		ArtistDAO dao = (ArtistDAO)SysContextUtil.getSpringApplicationContext().getBean("artistDAO");
		// 将spark任务返回的标准输出和错误输出到文件
		FileOutputStream outputStream = null, errorStream = null;
		// avoid unix error
		CommandLine commandline = CommandLine.parse("bash");
		commandline.addArgument("-c", false);

		String stdOutPath = logDir +"Std.txt";
		String errOutPath = logDir +"Err.txt";
		try {
			outputStream = new FileOutputStream(stdOutPath);
			errorStream = new FileOutputStream(errOutPath);
			StringBuilder sb = new StringBuilder(shell);
			sb.append(exeClass);
			sb.append(space);
			sb.append(params);
			sb.append(space);
			log.info("cmd: " + sb.toString());
			commandline.addArgument(sb.toString(), false);
			final DefaultExecutor executor = new DefaultExecutor();
			executor.setStreamHandler(new PumpStreamHandler(outputStream,
					errorStream));
//			executor.setWatchdog(new ExecuteWatchdog(commandTimeOut));
			int exitValue = executor.execute(commandline);
			log.info("===>" + exitValue);
		} catch (ExecuteException e) {
			log.error("Can not run command", e);
		} catch (IOException e) {
			log.error("Can not run ", e);
		} finally {//将task的运行信息放到文件中去
			try {
				outputStream.flush();
				log.info("stdout is in " + stdOutPath);
				errorStream.flush();
				log.info("errout is in " + errOutPath);
				outputStream.close();
				errorStream.close();
			} catch (IOException e) {
			}

		}
	}


	public String getLogDir() {
		return logDir;
	}

	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}

	public static String getShell() {
		return shell;
	}

	public String getExeClass() {
		return exeClass;
	}

	public void setExeClass(String exeClass) {
		this.exeClass = exeClass;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getExeJar() {
		return exeJar;
	}

	public void setExeJar(String exeJar) {
		this.exeJar = exeJar;
	}
}
