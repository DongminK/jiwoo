package jiwoo.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManager {

	private ConcurrentHashMap<String, Cache> mapCache = new ConcurrentHashMap<String, Cache>();

	private static CacheManager cacheManager = new CacheManager();

	private CacheManager() {
	}

	public static CacheManager getInstance() {
		return cacheManager;
	}

	Cache getCache(String type) {
		return mapCache.get(type);
	}

	synchronized public void setCache(Cache cache) throws Exception {

		String type = cache.getType();

		Cache inCache = mapCache.get(type);
		if (inCache != null)
			throw new Exception("Already exist cache type - " + type);

		mapCache.put(type, cache);
	}

	synchronized public void removeCache(Cache cache) throws Exception {

		String type = cache.getType();

		Cache inCache = mapCache.remove(type);

		if (inCache != null) {
			inCache.terminate();
		}
	}

	public HashMap<String, ArrayList<CacheInfo>> get(String key) {

		HashMap<String, ArrayList<CacheInfo>> mapCacheInfos = new HashMap<String, ArrayList<CacheInfo>>();

		for (Cache cache : mapCache.values()) {

			ArrayList<CacheInfo> ltCacheInfo = cache.get(key);

			if (ltCacheInfo.size() > 0) {
				ArrayList<CacheInfo> ltCacheInfos = new ArrayList<CacheInfo>();
				ltCacheInfos.addAll(ltCacheInfo);

				mapCacheInfos.put(cache.getType(), ltCacheInfos);
			}

		}

		return mapCacheInfos;
	}

	public HashMap<String, ArrayList<CacheInfo>> like(String key) {

		HashMap<String, ArrayList<CacheInfo>> mapCacheInfos = new HashMap<String, ArrayList<CacheInfo>>();

		for (Cache cache : mapCache.values()) {

			ArrayList<CacheInfo> ltCacheInfo = cache.like(key);

			if (ltCacheInfo.size() > 0) {
				ArrayList<CacheInfo> ltCacheInfos = new ArrayList<CacheInfo>();
				ltCacheInfos.addAll(ltCacheInfo);

				mapCacheInfos.put(cache.getType(), ltCacheInfos);
			}

		}

		return mapCacheInfos;
	}

	public void add(String type, CacheInfo cacheInfo) throws Exception {
		Cache cache = mapCache.get(type);

		if (cache == null)
			throw new Exception("Not found cache type - " + type);

		if (!cacheInfo.isBuildFactory())
			throw new Exception("Create object using CacheInfoFactory newInstance method");

		cache.add(cacheInfo);
	}

	public void remove(CacheInfo cacheInfo) throws Exception {

		String type = cacheInfo.getType();
		Cache cache = mapCache.get(type);

		if (cache == null)
			throw new Exception("Not found cache type - " + type);

		cache.remove(cacheInfo);
	}
}
