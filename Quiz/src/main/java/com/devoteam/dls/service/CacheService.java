package com.devoteam.dls.service;

import java.util.List;

import com.devoteam.dls.dao.Receiver;
import com.devoteam.dls.dao.Sender;
import com.devoteam.dls.domain.OnlineQuizzers;
import com.devoteam.dls.domain.OnlineStatus;
import com.devoteam.dls.domain.Quizzer;

public interface CacheService {
	void setQuizzer(OnlineQuizzers quizzer);
	List<OnlineQuizzers> getQuizzers();
	void removeQuizzer();
	void removeQuizzer(String userId);
	void setPushCache(Sender sender, Receiver receiver);
	Sender getPushCache(String receiverId);
	void updateOnlineStatus(String userId, OnlineStatus status);
}
