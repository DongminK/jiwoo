package jiwoo.cache;

import java.util.UUID;

abstract public class CacheInfo {

	private String uuid = UUID.randomUUID().toString();
	private Cache cache;
	private boolean isBuildFactory = false;

	public String getUuid() {
		return uuid;
	}

	public String getType() throws Exception {

		if (cache == null)
			throw new Exception("Not exist cache");

		return cache.getType();
	}

	public void destory() {
		if (cache != null)
			cache.remove(this);
	}

	void setCache(Cache cache) {
		this.cache = cache;
	}

	void buildFactory() {
		isBuildFactory = true;
	}

	boolean isBuildFactory() {
		return isBuildFactory;
	}

	void update() {
		if (cache != null)
			cache.add(this);
	}

	abstract public String[] keys();

	abstract public Object toObject();

}
