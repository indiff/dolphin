package com.tan.lock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import junit.framework.TestCase;

public class TestLock extends TestCase{
	private int ticket = 100;
	public void testLock() {
		
		Object lock = new Object();
		synchronized(lock) {
			// do something here that requires synchronized
			ticket--;
			System.out.println("The ticket : " + ticket);
		}
	}
	
	/**
	 * Reentrant 可重入
	 */
	public void testLockJDK5() {
		Lock lock =  new ReentrantLock();// ...
		lock.lock();
		try {
			ticket--;
			System.out.println("The ticket: " + ticket);
		} finally {
			lock.unlock();
		}
		
	}
	
	
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("one");
		list.add("two");
		list.add("three");
		list.add("four");
		list.add("five");
		list.add("six");
		list.add("seven");
		list.add("eight");
		list.add("nine");
		list.add("ten");
		list.add("eleven");
		list.add("twelve");
		Iterator<String> iterator = list.iterator();
		
		for (;iterator.hasNext();) {
			System.err.println(iterator.next());
		}
	}
	
}
