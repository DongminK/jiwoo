package jiwoo.cache;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

abstract public class Cache {

	private boolean isTerminate = false;
	private String type = "DEFAULT";
	protected ConcurrentHashMap<String, ConcurrentHashMap<String, CacheInfo>> mapCache = new ConcurrentHashMap<String, ConcurrentHashMap<String, CacheInfo>>();

	protected Cache(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	ArrayList<CacheInfo> get(String key) {

		if (isTerminate)
			return new ArrayList<CacheInfo>();

		ArrayList<CacheInfo> ltCacheInfo = new ArrayList<CacheInfo>();

		ConcurrentHashMap<String, CacheInfo> mapCacheInfos = mapCache.get(key);

		if (mapCacheInfos != null)
			ltCacheInfo.addAll(mapCacheInfos.values());

		return ltCacheInfo;
	}

	ArrayList<CacheInfo> like(String key) {

		if (isTerminate)
			return new ArrayList<CacheInfo>();

		ArrayList<CacheInfo> ltCacheInfo = new ArrayList<CacheInfo>();

		for (String cacheKey : mapCache.keySet()) {

			if (cacheKey.contains(key)) {
				ConcurrentHashMap<String, CacheInfo> mapCacheInfos = mapCache.get(cacheKey);
				ltCacheInfo.addAll(mapCacheInfos.values());
			}
		}

		return ltCacheInfo;
	}

	void add(CacheInfo cacheInfo) {

		if (isTerminate)
			return;

		cacheInfo.destory();
		cacheInfo.setCache(this);
		String[] keys = cacheInfo.keys();

		for (String key : keys) {

			ConcurrentHashMap<String, CacheInfo> mapCacheInfo = mapCache.get(key);

			if (mapCacheInfo == null) {
				mapCacheInfo = new ConcurrentHashMap<String, CacheInfo>();
				mapCache.put(key, mapCacheInfo);
			}

			mapCacheInfo.put(cacheInfo.getUuid(), cacheInfo);

		}
	}

	void remove(CacheInfo cacheInfo) {

		if (isTerminate)
			return;

		String[] keys = cacheInfo.keys();

		for (String key : keys) {
			ConcurrentHashMap<String, CacheInfo> mapCacheInfo = mapCache.get(key);
			if (mapCacheInfo != null) {
				mapCacheInfo.remove(cacheInfo.getUuid());
			}
		}
	}

	void terminate() {

		isTerminate = true;

		for (String key : mapCache.keySet()) {
			mapCache.remove(key);
		}

	}

}
