package com.devoteam.dls.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.devoteam.dls.domain.PlayingStats;

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
}
