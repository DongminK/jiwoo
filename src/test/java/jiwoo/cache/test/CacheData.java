package jiwoo.cache.test;

import jiwoo.cache.CacheInfo;

public class CacheData extends CacheInfo {

	private String name = "test";
	private String ip = "127.0.0.1";

	@Override
	public String[] keys() {
		// TODO Auto-generated method stub
		String[] keys = { name, ip };
		return keys;
	}

	public void setIpAddr(String ip) {
		this.ip = ip;
	}

	@Override
	public Object toObject() {
		// TODO Auto-generated method stub
		return "[" + name + "][" + ip + "]";
	}

}
