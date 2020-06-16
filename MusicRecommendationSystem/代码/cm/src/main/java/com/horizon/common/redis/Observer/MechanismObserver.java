package com.horizon.common.redis.Observer;

import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.horizon.common.base.Observer.GoodNotifier;
import com.horizon.common.base.Observer.Notifier;
import com.horizon.common.redis.Observer.constants.PileConstantsInfo;
import com.horizon.common.redis.model.MechanismStatus;
import com.horizon.common.util.JsonUtil;

/**
 * 机构状态 观察者
 * 
 * @author liy
 * 
 */
public class MechanismObserver {
	private static Logger log = Logger.getLogger(PileObserver.class);
	
	/**
	 * 桩离线数变化
	 */
	public void offNumPileChange(ObMessage messagees, String[] Keys) {
		// 获取对应站的信息
		String disStaPsStr = messagees.getRdao().get(Keys[0]);
		log.info("原机构-->" + disStaPsStr);
		log.info("原机构key-->" + Keys[0]);
		if (StringUtils.isNotBlank(disStaPsStr)) {
			MechanismStatus ma = new MechanismStatus();
			try {
				ma = (MechanismStatus) JsonUtil
						.genObjectFromJsonString(disStaPsStr,
								MechanismStatus.class);
			} catch (JsonGenerationException e) {
				log.error("Mech--offNum"+e.getMessage());
			} catch (JsonMappingException e) {
				log.error("Mech--offNum"+e.getMessage());
			} catch (IOException e) {
				log.error("Mech--offNum"+e.getMessage());
			}
			//更新桩总数
			if(StringUtils.isNoneBlank(messagees.getChangeOffNum())) {
				ma.setOffNum(ma.getOffNum() + Integer.parseInt(messagees.getChangeOffNum()));
			}
			//更新状态变化
			if(StringUtils.isNoneBlank(messagees.getChangeCa())) {
				ma.setCa(ma.getCa() + Integer.parseInt(messagees.getChangeCa()));
			}
			if(StringUtils.isNoneBlank(messagees.getChangeGa())) {
				ma.setGa(ma.getGa() + Integer.parseInt(messagees.getChangeGa()));
			}
			disStaPsStr = JsonUtil.genJsonString(ma);
			log.info("新机构-->" + disStaPsStr);
			// 更新所有redis里的状态
			messagees.getRdao().set(Keys[0], disStaPsStr);
			// 当改变时 通知上级
			createNotifierAll(messagees,"offNumPileChange");
        }
	}
	/**
	 * 桩故障数变化
	 */
	public void faultNumPileChange(ObMessage messagees, String[] Keys) {
		// 获取对应站的信息
			String disStaPsStr = messagees.getRdao().get(Keys[0]);
			log.info("原机构-->" + disStaPsStr);
			log.info("原机构key-->" + Keys[0]);
			if (StringUtils.isNotBlank(disStaPsStr)) {
				MechanismStatus ma = new MechanismStatus();
				try {
					ma = (MechanismStatus) JsonUtil
							.genObjectFromJsonString(disStaPsStr,
									MechanismStatus.class);
				} catch (JsonGenerationException e) {
					log.error("Mech--faultNum"+e.getMessage());
				} catch (JsonMappingException e) {
					log.error("Mech--faultNum"+e.getMessage());
				} catch (IOException e) {
					log.error("Mech--faultNum"+e.getMessage());
				}
				//更新桩总数
				if(StringUtils.isNoneBlank(messagees.getChangeFaultNum())) {
					ma.setFaultNum(ma.getFaultNum() + Integer.parseInt(messagees.getChangeFaultNum()));
				}
				//更新状态变化
				if(StringUtils.isNoneBlank(messagees.getChangeCa())) {
					ma.setCa(ma.getCa() + Integer.parseInt(messagees.getChangeCa()));
				}
				if(StringUtils.isNoneBlank(messagees.getChangeGa())) {
					ma.setGa(ma.getGa() + Integer.parseInt(messagees.getChangeGa()));
				}
				disStaPsStr = JsonUtil.genJsonString(ma);
				log.info("新机构-->" + disStaPsStr);
				// 更新所有redis里的状态
				messagees.getRdao().set(Keys[0], disStaPsStr);
				// 当改变时 通知上级
				createNotifierAll(messagees,"faultNumPileChange");
	        }
	}
	/**
	 * 桩总数变化
	 */
	public void allNumPileChange(ObMessage messagees, String[] Keys) {
		// 获取对应站的信息
			String disStaPsStr = messagees.getRdao().get(Keys[0]);
			log.info("原机构-->" + disStaPsStr);
			log.info("原机构key-->" + Keys[0]);
			if (StringUtils.isNotBlank(disStaPsStr)) {
				MechanismStatus ma = new MechanismStatus();
				try {
					ma = (MechanismStatus) JsonUtil
							.genObjectFromJsonString(disStaPsStr,
									MechanismStatus.class);
				} catch (JsonGenerationException e) {
					log.error("Mech--allNum"+e.getMessage());
				} catch (JsonMappingException e) {
					log.error("Mech--allNum"+e.getMessage());
				} catch (IOException e) {
					log.error("Mech--allNum"+e.getMessage());
				}
				//更新桩总数
				if(StringUtils.isNoneBlank(messagees.getChangeAllNum())) {
					ma.setAllNum(ma.getAllNum() + Integer.parseInt(messagees.getChangeAllNum()));
				}
				//更新状态变化
				if(StringUtils.isNoneBlank(messagees.getChangeCa())) {
					ma.setCa(ma.getCa() + Integer.parseInt(messagees.getChangeCa()));
				}
				if(StringUtils.isNoneBlank(messagees.getChangeGa())) {
					ma.setGa(ma.getGa() + Integer.parseInt(messagees.getChangeGa()));
				}
				disStaPsStr = JsonUtil.genJsonString(ma);
				log.info("新机构-->" + disStaPsStr);
				// 更新所有redis里的状态
				messagees.getRdao().set(Keys[0], disStaPsStr);
				// 当改变时 通知上级
				createNotifierAll(messagees,"allNumPileChange");
	        }
	}	
	
	/**
	 * 一般严重告警数变化
	 */
	public void caGaChange(ObMessage messagees, String[] Keys) {
		// 获取对应站的信息
			String disStaPsStr = messagees.getRdao().get(Keys[0]);
			log.info("原机构-->" + disStaPsStr);
			log.info("原机构key-->" + Keys[0]);
			if (StringUtils.isNotBlank(disStaPsStr)) {
				MechanismStatus ma = new MechanismStatus();
				try {
					ma = (MechanismStatus) JsonUtil
							.genObjectFromJsonString(disStaPsStr,
									MechanismStatus.class);
				} catch (JsonGenerationException e) {
					log.error("Mech--allNum"+e.getMessage());
				} catch (JsonMappingException e) {
					log.error("Mech--allNum"+e.getMessage());
				} catch (IOException e) {
					log.error("Mech--allNum"+e.getMessage());
				}
				//更新状态变化
				if(StringUtils.isNoneBlank(messagees.getChangeCa())) {
					ma.setCa(ma.getCa() + Integer.parseInt(messagees.getChangeCa()));
				}
				if(StringUtils.isNoneBlank(messagees.getChangeGa())) {
					ma.setGa(ma.getGa() + Integer.parseInt(messagees.getChangeGa()));
				}
				disStaPsStr = JsonUtil.genJsonString(ma);
				log.info("新机构-->" + disStaPsStr);
				// 更新所有redis里的状态
				messagees.getRdao().set(Keys[0], disStaPsStr);
				// 当改变时 通知上级
				createNotifierAll(messagees,"caGaChange");
	        }
	}	

	/**
	 * 站总数变化
	 */
	public void tsnStationChange(ObMessage messagees, String[] Keys) {
		// 获取对应站的信息
		String disStaPsStr = messagees.getRdao().get(Keys[0]);
		log.info("原机构-->" + disStaPsStr);
		log.info("原机构key-->" + Keys[0]);
		if (StringUtils.isNotBlank(disStaPsStr)) {
			MechanismStatus ma = new MechanismStatus();
			try {
				ma = (MechanismStatus) JsonUtil
						.genObjectFromJsonString(disStaPsStr,
								MechanismStatus.class);
			} catch (JsonGenerationException e) {
				log.error("Mech--tsnStation"+e.getMessage());
			} catch (JsonMappingException e) {
				log.error("Mech--tsnStation"+e.getMessage());
			} catch (IOException e) {
				log.error("Mech--tsnStation"+e.getMessage());
			}
			//更新站总数
			if(StringUtils.isNoneBlank(messagees.getChangeTsn())) {
				ma.setTsn(ma.getTsn() + Integer.parseInt(messagees.getChangeTsn()));
			}
			//更新状态变化
			if(StringUtils.isNoneBlank(messagees.getChangeCa())) {
				ma.setCa(ma.getCa() + Integer.parseInt(messagees.getChangeCa()));
			}
			if(StringUtils.isNoneBlank(messagees.getChangeGa())) {
				ma.setGa(ma.getGa() + Integer.parseInt(messagees.getChangeGa()));
			}
			disStaPsStr = JsonUtil.genJsonString(ma);
			log.info("新机构-->" + disStaPsStr);
			// 更新所有redis里的状态
			messagees.getRdao().set(Keys[0], disStaPsStr);
			// 当改变时 通知上级
			createNotifierAll(messagees,"tsnStationChange");
        }
	}
	
	/**
	 * 创建监听者 
	 */
	public void createNotifierAll(ObMessage messagees,String thisName) {
		// 创建监听者
		Notifier goodNotifier = new GoodNotifier();
		switch (messagees.getThisPointMa()) {
		case 2:
			// 设置观察者级别
			messagees.setThisPointMa(PileConstantsInfo.THIS_POINT_3);
			// 机构 市
			MechanismObserver mObServerCity = new MechanismObserver();
			String[] mObServerCityKeys = { messagees.getMechanismCode()
					.getCityCode() };
			messagees.setFindStatus(mObServerCityKeys);
			goodNotifier.addListener(mObServerCity, thisName,
					messagees, mObServerCityKeys);
			break;
		case 3:
			// 设置观察者级别
			messagees.setThisPointMa(PileConstantsInfo.THIS_POINT_4);
			// 机构 省
			MechanismObserver mObServerPro = new MechanismObserver();
			String[] mObServerProKeys = { messagees.getMechanismCode()
					.getProvinceCode() };
			messagees.setFindStatus(mObServerProKeys);
			goodNotifier.addListener(mObServerPro, thisName,
					messagees, mObServerProKeys);
			break;
		}
		// 通知有改变
		goodNotifier.notifyX();
	}
}
