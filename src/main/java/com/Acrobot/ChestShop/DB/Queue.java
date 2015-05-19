
package com.Acrobot.ChestShop.DB;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.persistence.OptimisticLockException;

import com.Acrobot.ChestShop.ChestShop;
import com.Acrobot.ChestShop.Configuration.Properties;

/** @author Acrobot */
public class Queue implements Runnable {
	private static final ConcurrentLinkedQueue<Transaction> queue = new ConcurrentLinkedQueue<Transaction>();
	
	public static void addToQueue(Transaction t) {
		queue.add(t);
	}
	
	@Override
	public synchronized void run() {
		if (Properties.RECORD_TIME_TO_LIVE != -1) {
			deleteOld();
		}
		
		ChestShop.getDB().save(queue);
		queue.clear();
	}
	
	public synchronized static boolean deleteOld() {
		try {
			ChestShop.getDB().delete(getOld());
			return true;
		}
		catch (OptimisticLockException ex) {
			return false;
		}
	}
	
	public static List getOld() throws OptimisticLockException {
		return ChestShop.getDB().find(Transaction.class).where().lt("sec", (System.currentTimeMillis() / 1000L) - Properties.RECORD_TIME_TO_LIVE).findList();
	}
}
