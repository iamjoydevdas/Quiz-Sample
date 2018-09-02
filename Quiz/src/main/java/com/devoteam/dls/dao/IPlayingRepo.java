package com.devoteam.dls.dao;

import com.devoteam.dls.domain.PlayingStats;

public interface IPlayingRepo  {
	PlayingStats getPlayingStats(String userName);
}
