package com.act.ucenter.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class UpgradeManager {
	private static Logger log = LoggerFactory.getLogger(UpgradeManager.class);
	
	private static List<Runnable> actions = new ArrayList<Runnable>();
	
	public static void add(Runnable action){
		actions.add(action);
	}
	
	public static void upgrade() {
		for (Runnable action: actions){
			try {
				action.run();
			}catch (Exception e){
				log.warn("upgrade error",e);
			}
		}
	}
}
