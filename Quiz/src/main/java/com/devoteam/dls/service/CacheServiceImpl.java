package com.devoteam.dls.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devoteam.dls.dao.Receiver;
import com.devoteam.dls.dao.Sender;
import com.devoteam.dls.domain.OnlineQuizzers;
import com.devoteam.dls.domain.OnlineStatus;
import com.devoteam.dls.push.Broadcaster;

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
	public void setQuizzer(OnlineQuizzers quizzer) {
		cacheManager.getCache(quizzerCache).put(new Element(quizzer.getQuizzer_ID(), quizzer));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OnlineQuizzers> getQuizzers() {
		List<Long> keys = cacheManager.getCache(quizzerCache).getKeys();
		List<OnlineQuizzers> activeQuizzers = new ArrayList<>();
		for(Long key : keys) {
			Element element = cacheManager.getCache(quizzerCache).get(key);
			activeQuizzers.add((OnlineQuizzers)element.getObjectValue());
		}
		return activeQuizzers;
	}
	
	@Override
	public void removeQuizzer() {
		cacheManager.getCache(quizzerCache).removeAll();
	}

	@Override
	public void updateOnlineStatus(String userId, OnlineStatus status) {
		/*List<OnlineQuizzers> activeQuizzers = getQuizzers();
		//OnlineQuizzers active = activeQuizzers.stream().filter(quizzers -> quizzers.getEmployee().getUsername().equals(userId)).findFirst().get();
		OnlineQuizzers active=null;
		for(OnlineQuizzers q : activeQuizzers) {
			if(q.getEmployee().getUsername().equals(userId)) {
				active = q;
			}
		}
		activeQuizzers.removeIf(quizzers -> quizzers.getEmployee().getUsername().equals(userId));
		active.setOnlineStatus(status);
		setQuizzer(active);
		Broadcaster.broadcast("Update user");*/
		
		List<Long> keys = cacheManager.getCache(quizzerCache).getKeys();
		List<OnlineQuizzers> activeQuizzers = new ArrayList<>();
		OnlineQuizzers active = null;
		Long foundKey = null;
		for(Long key : keys) {
			Element element = cacheManager.getCache(quizzerCache).get(key);
			if(((OnlineQuizzers)element.getObjectValue()).getEmployee().getUsername().equals(userId)) {
				active = (OnlineQuizzers)element.getObjectValue();
				foundKey = key;
				break;
			}
		}
		active.setOnlineStatus(status);
		cacheManager.getCache(quizzerCache).put(new Element(foundKey, active));
		//cacheManager.getCache(quizzerCache).remove(foundKey);
		Broadcaster.broadcast("Update user");
	}

	@Override
	public void removeQuizzer(String userId) {
		/*List<OnlineQuizzers> activeQuizzers = getQuizzers();
		activeQuizzers.removeIf(quizzers -> quizzers.getEmployee().getUsername().equals(userId));
		Broadcaster.broadcast("Update user");*/
		List<Long> keys = cacheManager.getCache(quizzerCache).getKeys();
		List<OnlineQuizzers> activeQuizzers = new ArrayList<>();
		Long foundKey = null;
		for(Long key : keys) {
			Element element = cacheManager.getCache(quizzerCache).get(key);
			if(((OnlineQuizzers)element.getObjectValue()).getEmployee().getUsername().equals(userId)) {
				foundKey = key;
				break;
			}
		}
		cacheManager.getCache(quizzerCache).remove(foundKey);
		Broadcaster.broadcast("Update user");
	}
}
