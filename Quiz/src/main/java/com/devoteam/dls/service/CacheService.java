package com.devoteam.dls.service;

import java.util.List;

import com.devoteam.dls.dao.Receiver;
import com.devoteam.dls.dao.Sender;
import com.devoteam.dls.domain.Quizzer;

public interface CacheService {
	void setQuizzer(Quizzer quizzer);
	List<Quizzer> getQuizzers();
	void removeQuizzer();
	void setPushCache(Sender sender, Receiver receiver);
	Sender getPushCache(String receiverId);
}
