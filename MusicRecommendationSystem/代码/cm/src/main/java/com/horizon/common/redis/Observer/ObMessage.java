package com.horizon.common.redis.Observer;

import java.util.Map;
import com.horizon.common.base.RedisClientDao;
import com.horizon.common.redis.model.MechanismStatus;
import com.horizon.common.redis.model.PileStationMechanism;
import com.horizon.common.redis.model.PileStatus;
import com.horizon.common.redis.model.StationStatus;
import com.horizon.common.vo.PileHsfVO;
import com.horizon.common.vo.StationVO;

/**
 * 封装观察者需要的参数
 * 
 * @author liy
 * 
 */
public class ObMessage {
	private String pineId;// 桩id
	private String stationId;// 站id
	private PileStatus pineStatus;// 新桩状态
	private PileStatus oldPineStatus;// 旧桩状态
	private PileStationMechanism mechanismCode;// 机构码
	private String messageType;// 消息类型 MONITOR监控信息 ALARM告警状态 STATUS 状态
	private RedisClientDao rdao;
	private String[] findStatus;
	private int thisPointSta;// 标识站 当前观察者在哪级 //0桩 1区县 2市 3省
	private int thisPointMa;// 标识机构当前观察者在哪级 //0桩 1区县 2市 3省
	
	private String changeOffNum;//离线数
	private String changeFaultNum;//故障数
	private String changeAllNum;//总桩数
	private String changeGa;//一般告警数
	private String changeCa;//严重告警数
	private String changeTsn;//总站数
	private String changeFreeNum;//空闲数
	private String changeChargeNum;//充电数
	private String changeMaintainNum;//维护数
	private String changeFaultCount;//故障次数
	private String changeOffCount;//离线次数
	private String changeChargeCount;//充电次数
	private int oldStaFauSta;//旧站的告警状态
	private int newStaFauSta;//旧站的告警状态
	
	public int getOldStaFauSta() {
		return oldStaFauSta;
	}

	public void setOldStaFauSta(int oldStaFauSta) {
		this.oldStaFauSta = oldStaFauSta;
	}

	private Map<String, String> pileMap; // 桩的变化值
	private Map<String, String> stationMap;// 站的变化值
	private Map<String, String> mechMap;// 机构的变化值

	private StationStatus oldStationStatus;// 旧桩站状态
	private MechanismStatus oldMechStatus;// 旧桩站状态
	
	private String reStart;//标识是否需要在区县站 计算改变值
	
	private StationVO svo;
	
	private PileHsfVO  pvo;

	public String getPineId() {
		return pineId;
	}

	public void setPineId(String pineId) {
		this.pineId = pineId;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public PileStatus getPineStatus() {
		return pineStatus;
	}

	public void setPineStatus(PileStatus pineStatus) {
		this.pineStatus = pineStatus;
	}

	public PileStationMechanism getMechanismCode() {
		return mechanismCode;
	}

	public void setMechanismCode(PileStationMechanism mechanismCode) {
		this.mechanismCode = mechanismCode;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public RedisClientDao getRdao() {
		return rdao;
	}

	public void setRdao(RedisClientDao rdao) {
		this.rdao = rdao;
	}

	public PileStatus getOldPineStatus() {
		return oldPineStatus;
	}

	public void setOldPineStatus(PileStatus oldPineStatus) {
		this.oldPineStatus = oldPineStatus;
	}

	public String[] getFindStatus() {
		return findStatus;
	}

	public void setFindStatus(String[] findStatus) {
		this.findStatus = findStatus;
	}

	public int getThisPointSta() {
		return thisPointSta;
	}

	public void setThisPointSta(int thisPointSta) {
		this.thisPointSta = thisPointSta;
	}

	public int getThisPointMa() {
		return thisPointMa;
	}

	public void setThisPointMa(int thisPointMa) {
		this.thisPointMa = thisPointMa;
	}

	public Map<String, String> getPileMap() {
		return pileMap;
	}

	public void setPileMap(Map<String, String> pileMap) {
		this.pileMap = pileMap;
	}

	public Map<String, String> getStationMap() {
		return stationMap;
	}

	public void setStationMap(Map<String, String> stationMap) {
		this.stationMap = stationMap;
	}

	public Map<String, String> getMechMap() {
		return mechMap;
	}

	public void setMechMap(Map<String, String> mechMap) {
		this.mechMap = mechMap;
	}

	public StationStatus getOldStationStatus() {
		return oldStationStatus;
	}

	public void setOldStationStatus(StationStatus oldStationStatus) {
		this.oldStationStatus = oldStationStatus;
	}

	public MechanismStatus getOldMechStatus() {
		return oldMechStatus;
	}

	public void setOldMechStatus(MechanismStatus oldMechStatus) {
		this.oldMechStatus = oldMechStatus;
	}

	public StationVO getSvo() {
		return svo;
	}

	public void setSvo(StationVO svo) {
		this.svo = svo;
	}

	public PileHsfVO getPvo() {
		return pvo;
	}

	public void setPvo(PileHsfVO pvo) {
		this.pvo = pvo;
	}

	public String getReStart() {
		return reStart;
	}

	public void setReStart(String reStart) {
		this.reStart = reStart;
	}

	public String getChangeOffNum() {
		return changeOffNum;
	}

	public void setChangeOffNum(String changeOffNum) {
		this.changeOffNum = changeOffNum;
	}

	public String getChangeFaultNum() {
		return changeFaultNum;
	}

	public void setChangeFaultNum(String changeFaultNum) {
		this.changeFaultNum = changeFaultNum;
	}

	public String getChangeAllNum() {
		return changeAllNum;
	}

	public void setChangeAllNum(String changeAllNum) {
		this.changeAllNum = changeAllNum;
	}

	public String getChangeGa() {
		return changeGa;
	}

	public void setChangeGa(String changeGa) {
		this.changeGa = changeGa;
	}

	public String getChangeCa() {
		return changeCa;
	}

	public void setChangeCa(String changeCa) {
		this.changeCa = changeCa;
	}

	public String getChangeTsn() {
		return changeTsn;
	}

	public void setChangeTsn(String changeTsn) {
		this.changeTsn = changeTsn;
	}

	public String getChangeFreeNum() {
		return changeFreeNum;
	}

	public void setChangeFreeNum(String changeFreeNum) {
		this.changeFreeNum = changeFreeNum;
	}

	public String getChangeChargeNum() {
		return changeChargeNum;
	}

	public void setChangeChargeNum(String changeChargeNum) {
		this.changeChargeNum = changeChargeNum;
	}

	public String getChangeMaintainNum() {
		return changeMaintainNum;
	}

	public void setChangeMaintainNum(String changeMaintainNum) {
		this.changeMaintainNum = changeMaintainNum;
	}

	public String getChangeFaultCount() {
		return changeFaultCount;
	}

	public void setChangeFaultCount(String changeFaultCount) {
		this.changeFaultCount = changeFaultCount;
	}

	public String getChangeOffCount() {
		return changeOffCount;
	}

	public void setChangeOffCount(String changeOffCount) {
		this.changeOffCount = changeOffCount;
	}

	public String getChangeChargeCount() {
		return changeChargeCount;
	}

	public void setChangeChargeCount(String changeChargeCount) {
		this.changeChargeCount = changeChargeCount;
	}

	public int getNewStaFauSta() {
		return newStaFauSta;
	}

	public void setNewStaFauSta(int newStaFauSta) {
		this.newStaFauSta = newStaFauSta;
	}

	

}
