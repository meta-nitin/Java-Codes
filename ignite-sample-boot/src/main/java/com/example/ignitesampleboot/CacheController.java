package com.example.ignitesampleboot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.cache.Cache.Entry;

import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

	@Autowired
	@Qualifier("igniteInstance")
	private IgniteCache<String, String> igniteCache;
	
	@GetMapping("write/{key}/{value}")
	public void writeInCache(@PathVariable String key, @PathVariable String value) {
		long startTime = System.currentTimeMillis();
		
		igniteCache.put(key, value);
		
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Total time taken for writing = " +totalTime);
	}
	
	@GetMapping("read/{key}")
	public String readFromCache(@PathVariable String key) {
		String value = "Value for " + key + " is --> \n" + igniteCache.get(key);
		return value;
	}
	
	@GetMapping("clear/{key}")
	public boolean clearCache(@PathVariable String key) {
		return igniteCache.remove(key);
	}
	
	@GetMapping("getAll")
	public Map<String, String> getAllContentsOfCache() {
		Map<String,String> cacheMap = new HashMap<>();
        Iterator<Entry<String, String>> itr = igniteCache.iterator();
		while(itr.hasNext()) {
        	Entry<String, String> entry = itr.next();
        	cacheMap.put(entry.getKey(), entry.getValue());
       }
       return cacheMap;
	}
}
