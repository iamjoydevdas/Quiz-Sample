package com.devoteam.dls.service;

import java.util.List;

import com.devoteam.dls.domain.PlayingStats;
import com.devoteam.dls.domain.Quizzer;

public interface QuizzerService {
	List<Quizzer> fetchAllQuizzer();
	List<Quizzer> fetchAllQuizzerExceptSelf(String username);
	Quizzer fetchQuizzer(String username);
	PlayingStats getPlayingStats();
}
