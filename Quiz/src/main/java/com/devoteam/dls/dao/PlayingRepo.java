package com.devoteam.dls.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.devoteam.dls.domain.PlayingStats;
import com.devoteam.dls.domain.Questions;
import com.devoteam.dls.domain.QuizSet;
import com.devoteam.dls.domain.Requests;

@Repository
public class PlayingRepo implements IPlayingRepo {
	@Autowired
	private JdbcTemplate jdbc;

	@Override
	public PlayingStats getPlayingStats(String userName) {
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
	
	public void sendPlayingRequest(Requests request) {
		//creating playingRequest
		jdbc.update("insert into playingrequests(senderid, receiverid, requestedat) " + 
				"values(?, ?, ?)", request.getSender(), request.getReceiver(), request.getRequestTime());
	}
	
	public void denySession(Requests request) {
		jdbc.update("update playingrequests set sessionid=0 " + 
				"where requestid=(select requestid from playingrequests where " + 
				"senderId=? and receiverId=? " + 
				"order by requestid desc limit 1)", request.getSender(), request.getReceiver());
	}
	
	public void acceptedChallenge(Requests request) {
		//fetching requestid
		int requestid = jdbc.queryForObject("select requestid from playingrequests where senderid=? and receiverid=? order by requestid desc limit 1", new Object[] { request.getSender(), request.getReceiver() },
		(rs, rowNum) -> rs.getInt("requestid"));
		System.out.println("requestid : "+requestid);
		//creating session
		jdbc.update("insert into session(quiztype, playingRequestid) values(?, ?)", request.getChallengeType(), requestid);
		//creating Questions
		jdbc.update("insert into sessionquestions(sessionQuestionId, questionId) \n" + 
				"values((select sessionId from session where playingRequestid=?),(select questionsetid from questionset where questionsetid not in \n" + 
				"(select questionId from sessionquestions where sessionquestionid=(select sessionId from session where playingRequestid=?)) ORDER BY random() limit 1))", requestid, requestid);
		jdbc.update("update playingrequests set sessionid=(select sessionId from session where playingRequestid=?) " + 
				"where requestid=?", requestid, requestid);
	}
	
	public Questions getQuestion(Requests request) {
		//fetching requestid
		int requestid = jdbc.queryForObject("select requestid from playingrequests where senderid=? and receiverid=? order by requestid desc limit 1", new Object[] { request.getSender(), request.getReceiver() },
		(rs, rowNum) -> rs.getInt("requestid"));
		System.out.println("requestid : "+requestid);
		
		
		return jdbc.queryForObject("select qs.question, qs.opt1, qs.opt2, qs.opt3, qs.opt4, qs.answer from session s, sessionquestions sq, playingrequests pr, questionset qs " + 
				"where pr.sessionid = s.sessionid and s.sessionid = sq.sessionquestionid and qs.questionSetId=sq.questionid " + 
				"and pr.requestid="+requestid+" order by sq.helperid desc limit 1",  (rs, rownum) -> {
					Questions q = new Questions();
					q.setQuestion(rs.getString("question"));
					q.setAnswer1(rs.getString("opt1"));
					q.setAnswer2(rs.getString("opt2"));
					q.setAnswer3(rs.getString("opt3"));
					q.setAnswer4(rs.getString("opt4"));
					q.setAnswer(rs.getString("answer"));
					return q;
		});
	}
	
	public void registerSenderAnswer(Requests request, String answer) {
		int requestid = jdbc.queryForObject("select requestid from playingrequests where senderid=? and receiverid=? order by requestid desc limit 1", new Object[] { request.getSender(), request.getReceiver() },
				(rs, rowNum) -> rs.getInt("requestid"));
				
		jdbc.update("update sessionquestions set " + 
				"senderanswer=? " + 
				"where helperid=" + 
				"(select helperid from sessionquestions where sessionquestionid=(select sessionId from session where playingRequestid=?) " + 
				"order by helperid desc limit 1)", answer, requestid);		
		
		String receiverAnswer = jdbc.queryForObject("select receiverAnswer from sessionquestions where sessionquestionid=(select sessionId from session where playingRequestid=?) " + 
				"order by helperid desc limit 1", new Object[] { requestid },
				(rs, rowNum) -> rs.getString("receiverAnswer"));
		
		if(receiverAnswer != null) {
			int count = jdbc.queryForObject("select count(*) as cnt from sessionquestions where " + 
					"sessionquestionid=(select sessionId from session where playingRequestid=?)", new Object[] { requestid },
					(rs, rowNum) -> rs.getInt("cnt"));
			
			if(count != 3) {
				//generate new question
				jdbc.update("insert into sessionquestions(sessionQuestionId, questionId) \n" + 
						"values((select sessionId from session where playingRequestid=?),(select questionsetid from questionset where questionsetid not in \n" + 
						"(select questionId from sessionquestions where sessionquestionid=(select sessionId from session where playingRequestid=?)) ORDER BY random() limit 1))", requestid, requestid);
			}
		}
				
	}
	
	public void registerReceiverAnswer(Requests request, String answer) {
		int requestid = jdbc.queryForObject("select requestid from playingrequests where senderid=? and receiverid=? order by requestid desc limit 1", new Object[] { request.getSender(), request.getReceiver() },
				(rs, rowNum) -> rs.getInt("requestid"));
				
		jdbc.update("update sessionquestions set " + 
				"receiveranswer=? " + 
				"where helperid=" + 
				"(select helperid from sessionquestions where sessionquestionid=(select sessionId from session where playingRequestid=?) " + 
				"order by helperid desc limit 1)", answer, requestid);		
		
		String senderanswer = jdbc.queryForObject("select senderanswer from sessionquestions where sessionquestionid=(select sessionId from session where playingRequestid=?) " + 
				"order by helperid desc limit 1", new Object[] { requestid },
				(rs, rowNum) -> rs.getString("senderanswer"));
		
		if(senderanswer != null) {
			int count = jdbc.queryForObject("select count(*) as cnt from sessionquestions where " + 
					"sessionquestionid=(select sessionId from session where playingRequestid=?)", new Object[] { requestid },
					(rs, rowNum) -> rs.getInt("cnt"));
			
			if(count != 3) {
				//generate new question
				jdbc.update("insert into sessionquestions(sessionQuestionId, questionId) \n" + 
						"values((select sessionId from session where playingRequestid=?),(select questionsetid from questionset where questionsetid not in \n" + 
						"(select questionId from sessionquestions where sessionquestionid=(select sessionId from session where playingRequestid=?)) ORDER BY random() limit 1))", requestid, requestid);
			}
		}		
	}
}
