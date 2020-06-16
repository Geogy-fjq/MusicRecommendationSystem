package com.horizon.common.base.Observer;

import org.apache.log4j.Logger;

public class GoodNotifier extends Notifier {
	private static Logger log = Logger.getLogger(GoodNotifier.class);

	@Override
	public void addListener(Object object, String methodName, Object... args) {
		//log.info("有新的观察者");
		EventHandler handler = this.getEventHandler();
		handler.addEvent(object, methodName, args);
	}

	@Override
	public void notifyX() {
		//log.info("通知观察者，数据改变了");
		try {
			EventHandler handler = this.getEventHandler();
			handler.notifyX();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

}