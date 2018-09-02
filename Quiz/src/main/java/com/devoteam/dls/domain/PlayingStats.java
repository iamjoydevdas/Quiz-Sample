package com.devoteam.dls.domain;

public class PlayingStats {
	private String name;
	private int totalPlayed;
	private int totalWin;
	private int totalLoss;
	private double percentage;
	
	public int getTotalPlayed() {
		return totalPlayed;
	}
	public void setTotalPlayed(int totalPlayed) {
		this.totalPlayed = totalPlayed;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalWin() {
		return totalWin;
	}
	public void setTotalWin(int totalWin) {
		this.totalWin = totalWin;
	}
	public int getTotalLoss() {
		return totalLoss;
	}
	public void setTotalLoss(int totalLoss) {
		this.totalLoss = totalLoss;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	@Override
	public String toString() {
		return "PlayingStats [name=" + name + ", totalPlayed=" + totalPlayed + ", totalWin=" + totalWin + ", totalLoss="
				+ totalLoss + ", percentage=" + percentage + "]";
	}
}
