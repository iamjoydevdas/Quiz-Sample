package com.devoteam.dls.service;

import java.util.List;

import com.devoteam.dls.domain.Quizzer;

public interface CacheService {
	void setQuizzer(Quizzer quizzer);
	List<Quizzer> getQuizzers();
}
