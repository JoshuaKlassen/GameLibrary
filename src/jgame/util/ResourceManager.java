package jgame.util;

import java.util.HashMap;
import java.util.Map;

public final class ResourceManager {

	private static Map<String, IResourceLoader> resourceLoaders = new HashMap<String, IResourceLoader>();
	private static Map<String, Object> resourceCache = new HashMap<String, Object>();
	
	public static void registerLoader(String type, IResourceLoader resourceLoader){
		resourceLoaders.put(type, resourceLoader);
	}
	
	public static void load(String type, String filePath, String key){
		resourceCache.put(key, resourceLoaders.get(type).load(filePath));
	}
	
	public static void unload(String key){
		resourceCache.remove(key);
	}
	
	public static Object get(String key){
		return resourceCache.get(key);
	}
}