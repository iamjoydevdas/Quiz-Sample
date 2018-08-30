package com.devoteam.dls.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import net.sf.ehcache.CacheManager;


@Configuration
@EnableCaching
public class CacheConfiguration {
	@Value("${cache.classPathConfigLocation}")
	private String cacheClassPathConfigLocation;
	
	@Bean
	public CacheManager cacheManager() {
		return ehCacheManagerFactory().getObject();
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactory() {
		EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
		factory.setConfigLocation(new ClassPathResource(cacheClassPathConfigLocation));
		factory.setShared(true);
		return factory;
	}
}
