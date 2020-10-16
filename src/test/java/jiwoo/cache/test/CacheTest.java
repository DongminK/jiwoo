package jiwoo.cache.test;

import java.util.ArrayList;
import java.util.HashMap;

import jiwoo.cache.CacheInfo;
import jiwoo.cache.CacheManager;
import jiwoo.cache.DefaultCache;

public class CacheTest {

	public static void main(String[] args) throws Exception {

		CacheManager cacheManager = CacheManager.getInstance();
		cacheManager.setCache(new DefaultCache());
		cacheManager.setCache(new ServerCache());

		CacheData defaultData = new CacheData();
		CacheData serverData = new CacheData();

		cacheManager.add(DefaultCache.TYPE, defaultData);
		cacheManager.add(ServerCache.TYPE, serverData);

		HashMap<String, ArrayList<CacheInfo>> mapCacheInfos = cacheManager.get("test");

		if (mapCacheInfos.size() > 0) {

			for (ArrayList<CacheInfo> ltCacheInfo : mapCacheInfos.values()) {

				for (CacheInfo cacheInfo : ltCacheInfo) {
					System.out.println("[" + cacheInfo.getUuid() + "][" + cacheInfo.getType() + "]" + cacheInfo.toObject());
				}

			}

		} else {
			System.out.println("data is empty");
		}

		serverData.destory();
		defaultData.setIpAddr("192.168.9.32");

		System.out.println("==========================");

		mapCacheInfos = cacheManager.get("127.0.0.1");

		if (mapCacheInfos.size() > 0) {

			for (ArrayList<CacheInfo> ltCacheInfo : mapCacheInfos.values()) {

				for (CacheInfo cacheInfo : ltCacheInfo) {
					System.out.println("[" + cacheInfo.getUuid() + "][" + cacheInfo.getType() + "]" + cacheInfo.toObject());
				}

			}

		} else {
			System.out.println("data is empty");
		}

		System.out.println("==========================");

		mapCacheInfos = cacheManager.get("192.168.9.32");

		if (mapCacheInfos.size() > 0) {

			for (ArrayList<CacheInfo> ltCacheInfo : mapCacheInfos.values()) {

				for (CacheInfo cacheInfo : ltCacheInfo) {
					System.out.println("[" + cacheInfo.getUuid() + "][" + cacheInfo.getType() + "]" + cacheInfo.toObject());
				}

			}

		} else {
			System.out.println("data is empty");
		}

		cacheManager.remove(defaultData);
		System.out.println("==========================");

		mapCacheInfos = cacheManager.get("192.168.9.32");

		if (mapCacheInfos.size() > 0) {

			for (ArrayList<CacheInfo> ltCacheInfo : mapCacheInfos.values()) {

				for (CacheInfo cacheInfo : ltCacheInfo) {
					System.out.println("[" + cacheInfo.getUuid() + "][" + cacheInfo.getType() + "]" + cacheInfo.toObject());
				}

			}

		} else {
			System.out.println("data is empty");
		}

	}
}
