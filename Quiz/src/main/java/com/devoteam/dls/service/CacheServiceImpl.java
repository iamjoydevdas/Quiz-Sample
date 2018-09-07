package com.devoteam.dls.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devoteam.dls.dao.Receiver;
import com.devoteam.dls.dao.Sender;
import com.devoteam.dls.domain.Quizzer;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service
public class CacheServiceImpl implements CacheService {
	@Autowired
	private CacheManager cacheManager;
	
	@Value("${cache.quizzer.name}")
	private String quizzerCache;
	
	@Value("${cache.push.name}")
	private String pushCache;

	@Override
	public void setPushCache(Sender sender, Receiver receiver) {
		cacheManager.getCache(pushCache).put(new Element(receiver.getReceiverId(),sender));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Sender getPushCache(String receiverId) {
		Sender s = null;
		Element e =  cacheManager.getCache(pushCache).get(receiverId);
		if(e!=null) {
			s = ((Sender) e.getObjectValue());
		}
		return s;
	}

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
