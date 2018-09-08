package com.devoteam.dls.dao;

import java.util.List;

import com.devoteam.dls.domain.PlayingStats;
import com.devoteam.dls.domain.Questions;
import com.devoteam.dls.domain.QuizSet;

public interface IPlayingRepo  {
	PlayingStats getPlayingStats(String userName);
	List<QuizSet> getQuizSet();
	List<Questions> getQuestions(long quizId);
}
