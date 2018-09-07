package com.devoteam.dls.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.devoteam.dls.domain.PlayingStats;
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
}
