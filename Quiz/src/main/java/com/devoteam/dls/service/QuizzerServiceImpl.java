package com.devoteam.dls.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devoteam.dls.dao.QuizzerRepository;
import com.devoteam.dls.domain.Quizzer;
import com.devoteam.dls.domain.QuizzerStatus;

@Service
@Transactional(readOnly = true)
public class QuizzerServiceImpl implements QuizzerService {
	private static final Logger LOG = LogManager.getLogger(QuizzerServiceImpl.class);
	
	@Autowired
	private QuizzerRepository quizzerRepo;

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
		LOG.info("Quizzers are: " + outputQuizzer);
		return outputQuizzer;
	}

}
