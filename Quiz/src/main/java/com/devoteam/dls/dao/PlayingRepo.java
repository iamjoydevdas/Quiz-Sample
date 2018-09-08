package com.devoteam.dls.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.devoteam.dls.domain.PlayingStats;
import com.devoteam.dls.domain.Questions;
import com.devoteam.dls.domain.QuizSet;

@Repository
public class PlayingRepo implements IPlayingRepo {
	@Autowired
	private JdbcTemplate jdbc;

	@Override
	public PlayingStats getPlayingStats(String userName) {
		System.out.println("ballll");
		return jdbc.queryForObject("select total_played, total_win, (total_played - total_win) as loss, last_played from quizzers where emp_id=2", (rs, rownum) -> {
			PlayingStats ps = new PlayingStats();
			ps.setName(rs.getString("last_played"));
			ps.setTotalWin(rs.getInt("total_win"));
			ps.setTotalPlayed(rs.getInt("total_played"));
			ps.setTotalLoss(rs.getInt("loss"));
			System.out.println("============================================================>>>>>>>>>>>>>"+ps.toString());
			return ps;
		});
	}

	@Override
	public List<QuizSet> getQuizSet() {
		List<QuizSet> quizSets = new ArrayList<>();
		jdbc.query("SELECT questionSet, quizName from QuizType", (rs, rownum) -> {
			QuizSet qs = new QuizSet();
			qs.setQuizId(rs.getInt("questionSet"));
			qs.setQuizSetName(rs.getString("quizName"));
			return qs;
			
		}).forEach(quizSets::add);
		return quizSets;
	}

	@Override
	public List<Questions> getQuestions(long quizId) {
		// TODO Auto-generated method stub
		List<Questions> questions = new ArrayList<>();
		jdbc.query("select questionId, question, opt1, opt2, opt3, opt4, answer from QuestionSet where questionId = ?",  new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, quizId);
			}
		}, (rs, rownum) -> {
			Questions q = new Questions();
			q.setQuiestionId(rs.getInt("questionId"));
			q.setQuestion(rs.getString("question"));
			q.setAnswer1(rs.getString("opt1"));
			q.setAnswer2(rs.getString("opt2"));
			q.setAnswer3(rs.getString("opt3"));
			q.setAnswer4(rs.getString("opt4"));
			q.setAnswer(rs.getString("answer"));
			return q;
		}).forEach(questions::add);
		return questions;
	}
}
