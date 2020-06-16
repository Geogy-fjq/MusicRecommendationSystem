package com.horizon.common.redis.Observer;

import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.horizon.common.base.RedisClientDao;
import com.horizon.common.base.Observer.GoodNotifier;
import com.horizon.common.base.Observer.Notifier;
import com.horizon.common.constants.ConstantsInfo;
import com.horizon.common.redis.Observer.constants.PileConstantsInfo;
import com.horizon.common.redis.model.StationStatus;
import com.horizon.common.util.JsonUtil;
import com.horizon.common.vo.StationVO;

/**
 * 站观察者
 * 
 * @author liy
 * 
 */
public class StationObserver {
	private static Logger log = Logger.getLogger(PileObserver.class);
	//新增站
    public void addStation(ObMessage messagees) {
    	//新增站的状态
		StationStatus sta = messagees.getOldStationStatus();
		StationVO svo = messagees.getSvo();
		String staS = JsonUtil.genJsonString(sta);
		RedisClientDao rDao = messagees.getRdao();
		//区县站状态
		rDao.hset(svo.getDistrictCode() + ConstantsInfo.MECHANISM_STATION_STATUS_Z,svo.getUuid(),staS);
		//市站状态
		rDao.hset(svo.getCityCode() + ConstantsInfo.MECHANISM_STATION_STATUS_Z,svo.getUuid(),staS);
		//省站状态
		rDao.hset(svo.getProvinceCode() + ConstantsInfo.MECHANISM_STATION_STATUS_Z,svo.getUuid(),staS);
		//更新上级站总+1
	    messagees.setChangeTsn(PileConstantsInfo.ADD_1);
		createNotifierStationNum(messagees);
	}
    //编辑站
    public void editStation(ObMessage messagees, String[] Keys) {
		StationStatus sta = messagees.getOldStationStatus();
		StationVO svo = messagees.getSvo();
		String staS = JsonUtil.genJsonString(sta);
		RedisClientDao rDao = messagees.getRdao();
		//区县站状态
		rDao.hset(svo.getDistrictCode() + ConstantsInfo.MECHANISM_STATION_STATUS_Z,svo.getUuid(),staS);
		//市站状态
		rDao.hset(svo.getCityCode() + ConstantsInfo.MECHANISM_STATION_STATUS_Z,svo.getUuid(),staS);
		//省站状态
		rDao.hset(svo.getProvinceCode() + ConstantsInfo.MECHANISM_STATION_STATUS_Z,svo.getUuid(),staS);
	}
    //删除站
    public void deleteStation(ObMessage messagees, String[] Keys) {
    	//新增站的状态
		StationStatus sta = messagees.getOldStationStatus();
		StationVO svo = messagees.getSvo();
		String staS = JsonUtil.genJsonString(sta);
		RedisClientDao rDao = messagees.getRdao();
		//区县站状态
		rDao.hset(svo.getDistrictCode() + ConstantsInfo.MECHANISM_STATION_STATUS_Z,svo.getUuid(),staS);
		//市站状态
		rDao.hset(svo.getCityCode() + ConstantsInfo.MECHANISM_STATION_STATUS_Z,svo.getUuid(),staS);
		//省站状态
		rDao.hset(svo.getProvinceCode() + ConstantsInfo.MECHANISM_STATION_STATUS_Z,svo.getUuid(),staS);
		//更新上级站总+1
	    messagees.setChangeTsn(PileConstantsInfo.REDUCE_1);
	    //计算机构要更新的值
	    messagees = updataAlarmNum(messagees);
		createNotifierStationNum(messagees);
	}
    //空闲数变化
    public void freeNumChange(ObMessage messagees, String[] Keys) {
    	// 获取对应站的信息
		String disStaPsStr = messagees.getRdao().hget(Keys[0], Keys[1]);
		log.info("原站-->" + disStaPsStr);
		log.info("原站key-->" + Keys[0] + "--" + Keys[1]);
		if (StringUtils.isNotBlank(disStaPsStr)) {
			StationStatus sta = new StationStatus();
			try {
				sta = (StationStatus) JsonUtil.genObjectFromJsonString(disStaPsStr,StationStatus.class);
			} catch (JsonGenerationException e) {
				log.error("sObserver--freeNum-->"+e.getMessage());
			} catch (JsonMappingException e) {
				log.error("sObserver--freeNum-->"+e.getMessage());
			} catch (IOException e) {
				log.error("sObserver--freeNum-->"+e.getMessage());
			}
			// 区县站的值需要自己更新
			if (PileConstantsInfo.THIS_POINT_1 == messagees.getThisPointSta()) {
				//保存旧站的告警状态
				messagees.setOldStaFauSta(sta.getAlarmState());
				
				if(StringUtils.isNotBlank(messagees.getChangeFreeNum())) {
					//空闲数变化
					sta.setFreeNum(sta.getFreeNum() + Integer.parseInt(messagees.getChangeFreeNum()));
				}
				//计算该站的故障类型
				sta = updataNewFault(sta);
				//保存新站的告警状态
				messagees.setNewStaFauSta(sta.getAlarmState());
				//更新机构里要统计的一般告警数和严重告警数
				messagees = updataAlarmNum(messagees);
			} else {
				if(StringUtils.isNotBlank(messagees.getChangeFreeNum())) {
					//空闲数变化
					sta.setFreeNum(sta.getFreeNum() + Integer.parseInt(messagees.getChangeFreeNum()));
				}
				////保存新站的告警状态
				sta.setAlarmState(messagees.getNewStaFauSta());
			}
			disStaPsStr = JsonUtil.genJsonString(sta);
			log.info("新站-->" + disStaPsStr);
			// 更新所有redis里的状态
			messagees.getRdao().hset(Keys[0], Keys[1], disStaPsStr);
			// 当改变时 通知上级
			 createNotifierAll(messagees,"freeNumChange","caGaChange");
		}
	}
    //充电数变化
    public void chargeNumChange(ObMessage messagees, String[] Keys) {
    	// 获取对应站的信息
		String disStaPsStr = messagees.getRdao().hget(Keys[0], Keys[1]);
		log.info("原站-->" + disStaPsStr);
		log.info("原站key-->" + Keys[0] + "--" + Keys[1]);
		if (StringUtils.isNotBlank(disStaPsStr)) {
			StationStatus sta = new StationStatus();
			try {
				sta = (StationStatus) JsonUtil.genObjectFromJsonString(disStaPsStr,StationStatus.class);
			} catch (JsonGenerationException e) {
				log.error("sObserver--chargeNum-->"+e.getMessage());
			} catch (JsonMappingException e) {
				log.error("sObserver--chargeNum-->"+e.getMessage());
			} catch (IOException e) {
				log.error("sObserver--chargeNum-->"+e.getMessage());
			}
			// 区县站的值需要自己更新
			if (PileConstantsInfo.THIS_POINT_1 == messagees.getThisPointSta()) {
				//保存旧站的告警状态
				messagees.setOldStaFauSta(sta.getAlarmState());
				
				if(StringUtils.isNotBlank(messagees.getChangeChargeNum())) {
					//充电数变化
					sta.setChargeNum(sta.getChargeNum() + Integer.parseInt(messagees.getChangeChargeNum()));
				}
				//计算该站的故障类型
				sta = updataNewFault(sta);
				//保存新站的告警状态
				messagees.setNewStaFauSta(sta.getAlarmState());
				//更新机构里要统计的一般告警数和严重告警数
				messagees = updataAlarmNum(messagees);
			} else {
				if(StringUtils.isNotBlank(messagees.getChangeChargeNum())) {
					//充电数变化
					sta.setChargeNum(sta.getChargeNum() + Integer.parseInt(messagees.getChangeChargeNum()));
				}
				////保存新站的告警状态
				sta.setAlarmState(messagees.getNewStaFauSta());
			}
			disStaPsStr = JsonUtil.genJsonString(sta);
			log.info("新站-->" + disStaPsStr);
			// 更新所有redis里的状态
			messagees.getRdao().hset(Keys[0], Keys[1], disStaPsStr);
			// 当改变时 通知上级
			createNotifierAll(messagees,"chargeNumChange","caGaChange");
		}
	}
    //告警/故障数变化
    public void faultNumChange(ObMessage messagees, String[] Keys) {
    	// 获取对应站的信息
			String disStaPsStr = messagees.getRdao().hget(Keys[0], Keys[1]);
			log.info("原站-->" + disStaPsStr);
			log.info("原站key-->" + Keys[0] + "--" + Keys[1]);
			if (StringUtils.isNotBlank(disStaPsStr)) {
				StationStatus sta = new StationStatus();
				try {
					sta = (StationStatus) JsonUtil.genObjectFromJsonString(disStaPsStr,StationStatus.class);
				} catch (JsonGenerationException e) {
					log.error("sObserver--faultNum-->"+e.getMessage());
				} catch (JsonMappingException e) {
					log.error("sObserver--faultNum-->"+e.getMessage());
				} catch (IOException e) {
					log.error("sObserver--faultNum-->"+e.getMessage());
				}
				// 区县站的值需要自己更新
				if (PileConstantsInfo.THIS_POINT_1 == messagees.getThisPointSta()) {
					//保存旧站的告警状态
					messagees.setOldStaFauSta(sta.getAlarmState());
					
					if(StringUtils.isNotBlank(messagees.getChangeFaultNum())) {
						//故障数变化
						sta.setFaultNum(sta.getFaultNum() + Integer.parseInt(messagees.getChangeFaultNum()));
					}
					//计算该站的故障类型
					sta = updataNewFault(sta);
					//保存新站的告警状态
					messagees.setNewStaFauSta(sta.getAlarmState());
					//更新机构里要统计的一般告警数和严重告警数
					messagees = updataAlarmNum(messagees);
				} else {
					if(StringUtils.isNotBlank(messagees.getChangeFaultNum())) {
						//故障数变化
						sta.setFaultNum(sta.getFaultNum() + Integer.parseInt(messagees.getChangeFaultNum()));
					}
					////保存新站的告警状态
					sta.setAlarmState(messagees.getNewStaFauSta());
				}
				disStaPsStr = JsonUtil.genJsonString(sta);
				log.info("新站-->" + disStaPsStr);
				// 更新所有redis里的状态
				messagees.getRdao().hset(Keys[0], Keys[1], disStaPsStr);
				// 当改变时 通知上级
				createNotifierAll(messagees,"faultNumChange","faultNumPileChange");
			}
	}
    //离线数变化
    public void offNumChange(ObMessage messagees, String[] Keys) {
    	// 获取对应站的信息
		String disStaPsStr = messagees.getRdao().hget(Keys[0], Keys[1]);
		log.info("原站-->" + disStaPsStr);
		log.info("原站key-->" + Keys[0] + "--" + Keys[1]);
		if (StringUtils.isNotBlank(disStaPsStr)) {
			StationStatus sta = new StationStatus();
			try {
				sta = (StationStatus) JsonUtil.genObjectFromJsonString(disStaPsStr,StationStatus.class);
			} catch (JsonGenerationException e) {
				log.error("sObserver--offNum-->"+e.getMessage());
			} catch (JsonMappingException e) {
				log.error("sObserver--offNum-->"+e.getMessage());
			} catch (IOException e) {
				log.error("sObserver--offNum-->"+e.getMessage());
			}
			// 区县站的值需要自己更新
			if (PileConstantsInfo.THIS_POINT_1 == messagees.getThisPointSta()) {
				//保存旧站的告警状态
				messagees.setOldStaFauSta(sta.getAlarmState());
				
				if(StringUtils.isNotBlank(messagees.getChangeOffNum())) {
					//离线数变化
					sta.setOffNum(sta.getOffNum() + Integer.parseInt(messagees.getChangeOffNum()));
				}
				//计算该站的故障类型
				sta = updataNewFault(sta);
				//保存新站的告警状态
				messagees.setNewStaFauSta(sta.getAlarmState());
				//更新机构里要统计的一般告警数和严重告警数
				messagees = updataAlarmNum(messagees);
			} else {
				if(StringUtils.isNotBlank(messagees.getChangeOffNum())) {
					//离线数变化
					sta.setOffNum(sta.getOffNum() + Integer.parseInt(messagees.getChangeOffNum()));
				}
				////保存新站的告警状态
				sta.setAlarmState(messagees.getNewStaFauSta());
			}
			disStaPsStr = JsonUtil.genJsonString(sta);
			log.info("新站-->" + disStaPsStr);
			// 更新所有redis里的状态
			messagees.getRdao().hset(Keys[0], Keys[1], disStaPsStr);
			// 当改变时 通知上级
			createNotifierAll(messagees,"offNumChange","offNumPileChange");
		}
	}
    //桩总数变化
    public void allNumChange(ObMessage messagees, String[] Keys) {
    	// 获取对应站的信息
		String disStaPsStr = messagees.getRdao().hget(Keys[0], Keys[1]);
		log.info("原站-->" + disStaPsStr);
		log.info("原站key-->" + Keys[0] + "--" + Keys[1]);
		if (StringUtils.isNotBlank(disStaPsStr)) {
			StationStatus sta = new StationStatus();
			try {
				sta = (StationStatus) JsonUtil.genObjectFromJsonString(disStaPsStr,StationStatus.class);
			} catch (JsonGenerationException e) {
				log.error("sObserver--allNum-->"+e.getMessage());
			} catch (JsonMappingException e) {
				log.error("sObserver--allNum-->"+e.getMessage());
			} catch (IOException e) {
				log.error("sObserver--allNum-->"+e.getMessage());
			}
			// 区县站的值需要自己更新
			if (PileConstantsInfo.THIS_POINT_1 == messagees.getThisPointSta()) {
				//保存旧站的告警状态
				messagees.setOldStaFauSta(sta.getAlarmState());
				if(StringUtils.isNotBlank(messagees.getChangeAllNum())) {
					//桩总数变化
					sta.setAllNum(sta.getAllNum() + Integer.parseInt(messagees.getChangeAllNum()));
				}
				//计算该站的故障类型
				sta = updataNewFault(sta);
				//保存新站的告警状态
				messagees.setNewStaFauSta(sta.getAlarmState());
				//更新机构里要统计的一般告警数和严重告警数
				messagees = updataAlarmNum(messagees);
			} else {
				//桩总数变化
				if(StringUtils.isNotBlank(messagees.getChangeAllNum())) {
					sta.setAllNum(sta.getAllNum() + Integer.parseInt(messagees.getChangeAllNum()));
				}
				////保存新站的告警状态
				sta.setAlarmState(messagees.getNewStaFauSta());
			}
			disStaPsStr = JsonUtil.genJsonString(sta);
			log.info("新站-->" + disStaPsStr);
			// 更新所有redis里的状态
			messagees.getRdao().hset(Keys[0], Keys[1], disStaPsStr);
			// 当改变时 通知上级
			createNotifierAll(messagees,"allNumChange","allNumPileChange");
		}
	}

	
	/**
	 * 创建监听者
	 */
	public void createNotifierAll(ObMessage messagees,String thisName,String mechName) {
		// 创建监听者
		Notifier goodNotifier = new GoodNotifier();
		switch (messagees.getThisPointSta()) {
		case 1:
			// 设置观察者级别
			messagees.setThisPointSta(PileConstantsInfo.THIS_POINT_2);
			// 站 市
			StationObserver sObjServerCity = new StationObserver();
			String[] sObjServerCityKeys = {
					messagees.getMechanismCode().getCityCode()
							+ ConstantsInfo.MECHANISM_STATION_STATUS_Z,
					messagees.getStationId(), };
			messagees.setFindStatus(sObjServerCityKeys);
			goodNotifier.addListener(sObjServerCity, thisName,
					messagees, sObjServerCityKeys);

			messagees.setThisPointMa(PileConstantsInfo.THIS_POINT_2);
			// 机构 区县
			MechanismObserver mObServerDis = new MechanismObserver();
			String[] mObServerDisKeys = { messagees.getMechanismCode()
					.getDistrictCode() };
			messagees.setFindStatus(mObServerDisKeys);
			goodNotifier.addListener(mObServerDis, mechName,
					messagees, mObServerDisKeys);
			break;
		case 2:
			// 设置观察者级别
			messagees.setThisPointSta(PileConstantsInfo.THIS_POINT_3);
			// 站 省
			StationObserver sObjServerPro = new StationObserver();
			String[] sObjServerProKeys = {
					messagees.getMechanismCode().getProvinceCode()
							+ ConstantsInfo.MECHANISM_STATION_STATUS_Z,
					messagees.getStationId(), };
			messagees.setFindStatus(sObjServerProKeys);
			goodNotifier.addListener(sObjServerPro, thisName,
					messagees, sObjServerProKeys);
			break;

		}
		// 通知有改变
		goodNotifier.notifyX();
	}
	
	/**
	 * 创建监听者站总数发生变化
	 */
	public void createNotifierStationNum(ObMessage messagees) {
		// 创建监听者
		Notifier goodNotifier = new GoodNotifier();
		messagees.setThisPointMa(PileConstantsInfo.THIS_POINT_2);
		// 机构 区县
		MechanismObserver mObServerDis = new MechanismObserver();
		String[] mObServerDisKeys = { messagees.getMechanismCode()
				.getDistrictCode() };
		messagees.setFindStatus(mObServerDisKeys);
		goodNotifier.addListener(mObServerDis, "tsnStationChange",
				messagees, mObServerDisKeys);
		// 通知有改变
		goodNotifier.notifyX();
	}
   
    /**
	 * 更新机构里要统计的一般告警数和严重告警数
	 */
	public static ObMessage updataAlarmNum(ObMessage mess) {
		if(mess.getOldStaFauSta() != mess.getNewStaFauSta()) {
			//更新原状态
			if(mess.getOldStaFauSta() == PileConstantsInfo.STATION_FAULT_STATUS_1) {
				//原状态为 一般
				mess.setChangeGa(PileConstantsInfo.REDUCE_1);
			} else if (mess.getOldStaFauSta() == PileConstantsInfo.STATION_FAULT_STATUS_2) {
				//原状态为 严重
				mess.setChangeCa(PileConstantsInfo.REDUCE_1);
			}
			//更新新状态
			if(mess.getNewStaFauSta() == PileConstantsInfo.STATION_FAULT_STATUS_1) {
				//原状态为 一般
				mess.setChangeGa(PileConstantsInfo.ADD_1);
			} else if (mess.getNewStaFauSta() == PileConstantsInfo.STATION_FAULT_STATUS_2) {
				//原状态为 严重
				mess.setChangeCa(PileConstantsInfo.ADD_1);
			}
		}
		return mess;
	}
	
	/**
	 * 更新站的故障状态
	 */
	public static StationStatus updataNewFault(StationStatus sta) {
		if(sta.getAllNum() != 0) {
			 //计算故障率
			double rate = (double) sta.getFaultNum() / sta.getAllNum();
			log.info("rate-->" + rate);
			if (rate == PileConstantsInfo.STATION_FAULT_STATUS_RATE_0) {
				sta.setAlarmState(PileConstantsInfo.STATION_FAULT_STATUS_0);
			} else if (rate >= PileConstantsInfo.STATION_FAULT_STATUS_RATE_05) {
				sta.setAlarmState(PileConstantsInfo.STATION_FAULT_STATUS_2);
			} else {
				sta.setAlarmState(PileConstantsInfo.STATION_FAULT_STATUS_1);
			}
			return sta;
		} else {
			return null;
		}
	}
}
