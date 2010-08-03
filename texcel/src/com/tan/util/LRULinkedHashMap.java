package com.tan.util;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author dolphin
 *
 * 2010-4-30 下午04:57:27
 */
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {
	private final int maxCapacity;
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;
	private final Lock lock = new ReentrantLock();
	
	public LRULinkedHashMap(int maxCapacity) {
		super(maxCapacity, DEFAULT_LOAD_FACTOR);
		this.maxCapacity = maxCapacity;
	}
	
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return size() > maxCapacity;
	}
	
	@Override
	public V get(Object key) {
		try {
			lock.lock();
			return super.get(key);
		} finally {
			lock.unlock();
		}
	}
	
	@Override
	public V put(K key, V value) {
		try {
			lock.lock();
			return super.put(key, value);
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String args[]) {
//		LRULinkedHashMap<String,String> map = new LRULinkedHashMap<String,String>(10);
//		map.put("one", "1");
//		map.put("two", "2");
//		map.put("three", "2");
		
		System.err.println("最近最少使用页".toUpperCase());
		
		/*
		 * LRU : least recently used 最近最少使用页面置换的算法
		 * */
	}
}
