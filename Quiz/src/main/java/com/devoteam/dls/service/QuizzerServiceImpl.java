package com.devoteam.dls.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devoteam.dls.dao.IPlayingRepo;
import com.devoteam.dls.dao.QuizzerRepository;
import com.devoteam.dls.domain.PlayingStats;
import com.devoteam.dls.domain.Questions;
import com.devoteam.dls.domain.QuizSet;
import com.devoteam.dls.domain.Quizzer;
import com.devoteam.dls.domain.QuizzerStatus;

@Service
@Transactional(readOnly = true)
public class QuizzerServiceImpl implements QuizzerService {
	private static final Logger LOG = LogManager.getLogger(QuizzerServiceImpl.class);
	
	@Autowired
	private QuizzerRepository quizzerRepo;
	
	@Autowired
	private IPlayingRepo playingRepo;

	@Override
	public List<Quizzer> fetchAllQuizzer() {
		LOG.info("Fetching all quizzer");
		List<Quizzer> quizzers = quizzerRepo.findAll();
		LOG.info("Quizzers are: " + quizzers);
		return quizzers;
	}

	@Override
	public List<Quizzer> fetchAllQuizzerExceptSelf(String username) {
		LOG.info("Fetching all active quizzer except self");
		List<Quizzer> orizinalQuizzers = quizzerRepo.findAll();
		List<Quizzer> outputQuizzer = new ArrayList<>();
		for(Quizzer quizzer : orizinalQuizzers) {
			if(!quizzer.getEmployee().getUsername().equals(username) && quizzer.getQuizzer_status().equals(QuizzerStatus.ACTIVE)) {
				outputQuizzer.add(quizzer);
			}
		}
		LOG.info(">>>>>>>>>>>>>>>>>>Quizzers are: " + outputQuizzer);
		return outputQuizzer;
	}

	@Override
	public Quizzer fetchQuizzer(String username) {
		LOG.info("Fetching current user as quizzer");
		List<Quizzer> orizinalQuizzers = quizzerRepo.findAll();
		for(Quizzer quizzer : orizinalQuizzers) {
			if(quizzer.getEmployee().getUsername().equals(username) && quizzer.getQuizzer_status().equals(QuizzerStatus.ACTIVE)) {
				return quizzer;
			}
		}
		return null;
	}

	@Override
	public PlayingStats getPlayingStats() {
		System.out.println("Okey I am called");
		return playingRepo.getPlayingStats("");
	}

	@Override
	public List<QuizSet> getQuizSet() {
		return playingRepo.getQuizSet();
	}

	@Override
	public List<Questions> getQuestions(long quizId) {
		// TODO Auto-generated method stub
		return playingRepo.getQuestions(quizId);
	}
}
