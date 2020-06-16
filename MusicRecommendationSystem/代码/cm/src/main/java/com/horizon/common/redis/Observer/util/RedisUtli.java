package com.horizon.common.redis.Observer.util;

import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.horizon.common.base.RedisClientDao;
import com.horizon.common.base.Observer.GoodNotifier;
import com.horizon.common.base.Observer.Notifier;
import com.horizon.common.redis.Observer.ObMessage;
import com.horizon.common.redis.Observer.PileObserver;
import com.horizon.common.redis.Observer.constants.PileConstantsInfo;
import com.horizon.common.redis.model.PileStationMechanism;
import com.horizon.common.redis.model.PileStatus;
import com.horizon.common.util.JsonUtil;

/**
 * 从接口获取数据时 更新redis的工具类
 * 
 * @author liy
 * 
 */
public class RedisUtli {
	private static Logger log = Logger.getLogger(PileObserver.class);

	/**
	 * 初始化观察者对象们
	 * 
	 * @return
	 */
	public static void initObServer(ObMessage obMessage) {
		// 创建一个监听者
		Notifier goodNotifier = new GoodNotifier();
		obMessage.setThisPointSta(PileConstantsInfo.THIS_POINT_0);
		//obMessage.setThisPointMa(PileConstantsInfo.THIS_POINT_0);
		// 桩
		PileObserver pObServer = new PileObserver();
		String[] pObServerKeys = { obMessage.getStationId(),
				obMessage.getPineId() };
		obMessage.setFindStatus(pObServerKeys);
		goodNotifier.addListener(pObServer, "changePileStatus", obMessage,
				pObServerKeys);

		goodNotifier.notifyX();
	}

	/**
	 * 初始化观察者的参数
	 * 
	 * @param pileId
	 * @param rdao
	 * @return
	 */
	public static ObMessage initObMessage(String pileId, RedisClientDao rdao) {
		// 获取站的id
		String stationId = RedisUtli.getStationId(pileId, rdao);
		if(StringUtils.isNotBlank(stationId)) {
			// 获取原始桩状态
			String pilePsStr = rdao.hget(stationId, pileId);
			if (StringUtils.isNotBlank(pilePsStr)) {
				// 如果状态变化了 则进行修改操作 除工作状态 要更新soc的值
				PileStatus pilePsOld = new PileStatus();
				try {
					pilePsOld = (PileStatus) JsonUtil.genObjectFromJsonString(
							pilePsStr, PileStatus.class);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
				// 给观察者传递的参数
				ObMessage obMessage = new ObMessage();
				// 放入redis dao
				obMessage.setRdao(rdao);

				// 放入桩id
				obMessage.setPineId(pileId);
				// 放入站id
				obMessage.setStationId(stationId);
				// 放入机构码 省_市_区
				PileStationMechanism psmId = RedisUtli.getMechanismIds(pileId,
						stationId, rdao);
				obMessage.setMechanismCode(psmId);
				// 放入旧的桩状态
				obMessage.setOldPineStatus(pilePsOld);
				return obMessage;
			}
		}
		return null;
	}

	/**
	 * 根据桩id获取站id
	 * 
	 * @param pileId
	 * @param redisClientDAO
	 * @return
	 */
	public static String getStationId(String pileId,
			RedisClientDao redisClientDAO) {
		// 根据桩id 获取站信息
		Set<String> stationSet = redisClientDAO.hkeys(pileId);
		// 站id
		String station = null;
		if (stationSet != null && stationSet.size() > 0) {
			station = (String) stationSet.toArray()[0];
		}
		log.info("桩id-->" + pileId);
		log.info("站id-->" + station);
		return station;
	}

	/**
	 * 根据 桩id和站is获取机构id
	 * 
	 * @param pileId
	 * @param station
	 * @param redisClientDAO
	 * @return
	 */
	public static PileStationMechanism getMechanismIds(String pileId,
			String station, RedisClientDao redisClientDAO) {
		String mechanismIds = redisClientDAO.hget(pileId, station);
		// 机构信息
		String[] mids = mechanismIds.split("_");
		PileStationMechanism mMap = new PileStationMechanism();
		mMap.setProvinceCode(mids[0]);
		mMap.setCityCode(mids[1]);
		mMap.setDistrictCode(mids[2]);
		return mMap;
	}

}
