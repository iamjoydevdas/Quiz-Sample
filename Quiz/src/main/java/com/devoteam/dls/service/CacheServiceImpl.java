package com.devoteam.dls.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devoteam.dls.domain.Quizzer;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service
public class CacheServiceImpl implements CacheService {
	@Autowired
	private CacheManager cacheManager;
	
	@Value("${cache.quizzer.name}")
	private String quizzerCache;

	@Override
	public void setQuizzer(Quizzer quizzer) {
		cacheManager.getCache(quizzerCache).put(new Element(quizzer.getQuizzer_ID(), quizzer));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Quizzer> getQuizzers() {
		List<Long> keys = cacheManager.getCache(quizzerCache).getKeys();
		List<Quizzer> activeQuizzers = new ArrayList<>();
		for(Long key : keys) {
			Element element = cacheManager.getCache(quizzerCache).get(key);
			activeQuizzers.add((Quizzer)element.getObjectValue());
		}
		return activeQuizzers;
	}
	
	@Override
	public void removeQuizzer() {
		cacheManager.getCache(quizzerCache).removeAll();
	}
}
