package com.horizon.common.redis.Observer;

import java.text.SimpleDateFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.horizon.common.base.RedisClientDao;
import com.horizon.common.base.Observer.GoodNotifier;
import com.horizon.common.base.Observer.Notifier;
import com.horizon.common.constants.ConstantsInfo;
import com.horizon.common.redis.Observer.constants.PileConstantsInfo;
import com.horizon.common.redis.model.PileStatus;
import com.horizon.common.util.JsonUtil;
import com.horizon.common.vo.PileHsfVO;

/**
 * 桩状态 观察者
 * 
 * @author liy
 * 
 */
public class PileObserver {
	private static Logger log = Logger.getLogger(PileObserver.class);
	//新增桩
	public void addPile(ObMessage messagees) {
		//新增桩的状态
		PileStatus ps = messagees.getOldPineStatus();
		PileHsfVO pvo = messagees.getPvo();
		String staS = JsonUtil.genJsonString(ps);
		RedisClientDao rDao = messagees.getRdao();
		//保存桩信息
		rDao.hset(messagees.getStationId(),ps.getPileCode(),staS);
		//更新 桩 -> 站 -> 机构
		rDao.hset(ps.getPileCode(),messagees.getStationId(),pvo.getProvinceCode()+"_"+pvo.getCityCode()+"_"+pvo.getDistrictCode());
		//通知区县 桩总+1
		messagees.setChangeAllNum(PileConstantsInfo.ADD_1);
		createNotifierAll(messagees,"allNumChange");
	}
	//删除桩
    public void deletePile(ObMessage messagees, String[] Keys) {
		PileHsfVO pvo = messagees.getPvo();
		RedisClientDao rDao = messagees.getRdao();
		//删除桩信息
		rDao.del(messagees.getStationId());
		//更新 桩 -> 站 -> 机构
		rDao.del(pvo.getUuid());
		//通知区县 桩总-1
		messagees.setChangeAllNum(PileConstantsInfo.REDUCE_1);
		createNotifierAll(messagees,"allNumChange");
	}
    //编辑桩
    public void editPile(ObMessage messagees, String[] Keys) {
    	//修改桩的状态
		PileStatus ps = messagees.getOldPineStatus();
		String staS = JsonUtil.genJsonString(ps);
		RedisClientDao rDao = messagees.getRdao();
		//保存桩信息
		rDao.hset(messagees.getStationId(),ps.getPileCode(),staS);
	}
    //桩状态变化
    public void changePileStatus(ObMessage messagees, String[] Keys) {
    	// 获取对应站的信息
		String disStaPsStr = messagees.getRdao().hget(Keys[0], Keys[1]);
		log.info("原桩-->" + disStaPsStr);
		log.info("原桩key-->" + Keys[0] + "--" + Keys[1]);
		//更新上级
		updataOldWorkStatus(messagees);
		updataNewWorkStatus(messagees);
		//更新原桩的值
		messagees = countStatus(messagees);
		messagees.getRdao().hset(Keys[0], Keys[1],JsonUtil.genJsonString(messagees.getOldPineStatus()));
	}
	/**
	 * 更新新的工作状态
	 */
	public void updataNewWorkStatus(ObMessage messagees) {
		// 新工作状态
		if(StringUtils.isNotBlank(messagees.getPineStatus().getRunSta())) {
			switch (Integer.parseInt(messagees.getPineStatus().getRunSta())) {
			// 工作状态 0001-故障
			case ConstantsInfo.WORKSTATUS_ALARM_INT:
				// 所有的故障数+1
				messagees.setChangeFaultNum(PileConstantsInfo.ADD_1);
				//通知上级故障监听者
				//createNotifierChangeAlarmNum(messagees);
				createNotifierAll(messagees,"faultNumChange");
				break;
			// 工作状态 0002-待机 空闲
			case ConstantsInfo.WORKSTATUS_FREE_INT:
				// 所有的空闲数 +1
				messagees.setChangeFreeNum(PileConstantsInfo.ADD_1);
				//通知上级空闲监听者
				//createNotifierChangeFreeNum(messagees);
				createNotifierAll(messagees,"freeNumChange");
				break;
			// 工作状态 0003-工作
			case ConstantsInfo.WORKSTATUS_WORK_INT:
				// 所有的充电数 +1
				//sta.setChargeNum(sta.getChargeNum() + Integer.parseInt(PileConstantsInfo.REDUCE_1));
				messagees.setChangeChargeNum(PileConstantsInfo.ADD_1);
				//通知上级空闲监听者
				//createNotifierChangeChargeNum(messagees);
				createNotifierAll(messagees,"chargeNumChange");
				break;
			// 工作状态 0004-离线
			case ConstantsInfo.WORKSTATUS_OFF_LINE_INT:
				// 所有的离线数 +1
				messagees.setChangeOffNum(PileConstantsInfo.ADD_1);
				//通知上级故障监听者
				createNotifierAll(messagees,"offNumChange");
				break;
			}
		}
	}
    /**
	 * 更新原始工作状态
	 */
	public void updataOldWorkStatus(ObMessage messagees) {
		//StationStatus sta = messagees.getOldStationStatus();
		// 原工作状态
		if(StringUtils.isNotBlank(messagees.getOldPineStatus().getRunSta())) {
			switch (Integer.parseInt(messagees.getOldPineStatus().getRunSta())) {
			// 工作状态 0001-故障
			case ConstantsInfo.WORKSTATUS_ALARM_INT:
				// 所有的故障数-1
				messagees.setChangeFaultNum(PileConstantsInfo.REDUCE_1);
				//通知上级故障监听者
				createNotifierAll(messagees,"faultNumChange");
				break;
			// 工作状态 0002-待机 空闲
			case ConstantsInfo.WORKSTATUS_FREE_INT:
				// 所有的空闲数 -1
				messagees.setChangeFreeNum(PileConstantsInfo.REDUCE_1);
				//通知上级空闲监听者
				createNotifierAll(messagees,"freeNumChange");
				break;
			// 工作状态 0003-工作
			case ConstantsInfo.WORKSTATUS_WORK_INT:
				// 所有的充电数 -1
				messagees.setChangeChargeNum(PileConstantsInfo.REDUCE_1);
				//通知上级空闲监听者
				createNotifierAll(messagees,"chargeNumChange");
				break;
			// 工作状态 0004-离线
			case ConstantsInfo.WORKSTATUS_OFF_LINE_INT:
				// 所有的离线数 -1
				messagees.setChangeOffNum(PileConstantsInfo.REDUCE_1);
				//通知上级故障监听者
				createNotifierAll(messagees,"offNumChange");
				break;
			}
		}
	}

	/**
	 * 创建监听者   通知区县站
	 */
	public void createNotifierAll(ObMessage messagees,String funcName) {
		// 创建监听者
		Notifier goodNotifier = new GoodNotifier();
		// 设置观察者级别
		messagees.setThisPointSta(PileConstantsInfo.THIS_POINT_1);
		// 站 区县
		StationObserver sObjServerDis = new StationObserver();
		String[] sObjServerDisKeys = {
				messagees.getMechanismCode().getDistrictCode()
						+ ConstantsInfo.MECHANISM_STATION_STATUS_Z,
				messagees.getStationId()};
		messagees.setFindStatus(sObjServerDisKeys);
		goodNotifier.addListener(sObjServerDis, funcName, messagees,
				sObjServerDisKeys);
		// 通知有改变
		goodNotifier.notifyX();
	}

	/**
	 * 更新桩信息
	 */
	public ObMessage countStatus(ObMessage message) {
		// 原桩信息
		PileStatus ps = message.getOldPineStatus();
		// 如果前后状态一致，则置更新soc的时间
			if (!message.getPineStatus().getRunSta().equals(ps.getRunSta())) {
				/**
				 * 改成从新数据里取时间，目前时间没确定格式，
				 */
				//Date now = new Date();
				Long tim = message.getPineStatus().getTime();//System.currentTimeMillis();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");// 可以方便地修改日期格式
				String NewDate = dateFormat.format(tim);

				// 在一个月内的则+1 否则 清0
				// 更新桩下的信息
				if (NewDate.equals(String.valueOf(message.getOldPineStatus()
						.getTime()))) {
					switch (Integer.parseInt(message.getPineStatus().getRunSta())) {
					// 故障
					case ConstantsInfo.WORKSTATUS_ALARM_INT:
						// 故障次数+1
						ps.setFaultCount(ps.getFaultCount() + 1);
						break;
					// 离线
					case ConstantsInfo.WORKSTATUS_OFF_LINE_INT:
						// 离线次数 + 1
						ps.setOffCount(ps.getOffCount() + 1);
						break;
					// 充电
					case ConstantsInfo.WORKSTATUS_FULL_INT:
						ps.setChargeCount(ps.getChargeCount() + 1);
						break;
					}

				} else {
					ps.setFaultCount(0);
					ps.setOffCount(0);
					ps.setChargeCount(0);
				}
			}
			
		// 更新状态
		ps.setRunSta(message.getPineStatus().getRunSta());
		ps.setSoc(message.getPineStatus().getSoc());
		ps.setTime(message.getPineStatus().getTime());
		message.setOldPineStatus(ps);
		return message;

	}

}
