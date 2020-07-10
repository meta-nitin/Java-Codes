package com.example.ignitesampleboot;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IgniteStartStop {

	private Ignite ignite;
	
	@PostConstruct
	@Bean(name = "igniteInstance")
	public IgniteCache<String, String> startIgnite() {
		System.out.println("Starting Ignite");
		ignite = Ignition.start("src/main/resources/ignite-config.xml");
		System.out.println("Ignite instance: " +ignite.toString());
		ignite.active(true);	//deprecated
		IgniteCache<String, String> igniteCache = ignite.getOrCreateCache("tempCache");
		return igniteCache;
	}

	@PreDestroy
	public void stopIgnite() {
		System.out.println("Stopping Ignite");
		ignite.close();
	}
}
