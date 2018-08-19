CREATE TABLE IF NOT EXISTS employee (
  employee_ID serial,
  username TEXT,
  password TEXT,
  created_date DATE,
  last_modified_date date,
  PRIMARY KEY(employee_ID)
 );

--------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS Role (
	role_ID serial,
	role_type TEXT,
	created_date DATE,
	employee_ID INTEGER,
	PRIMARY KEY(role_ID),
	FOREIGN KEY(employee_ID) REFERENCES employee (employee_ID) 
);
---------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS Quizzers (
	quizzerId serial,
	empId INTEGER,
	lastPlayed DATE,
	totalPlayed INTEGER,
	totalWin INTEGER,
	PRIMARY KEY(quizzerId),
	FOREIGN KEY(empId) REFERENCES employee (employee_ID) 
);
---------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS PlayingRequests (
	requestId serial,
	senderId INTEGER,
	receiverId INTEGER,
	requestedAt DATE,
	receiverAccpted BOOLEAN,
	sessionId INTEGER,
	PRIMARY KEY(requestId)
);
---------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS QuestionSet (
	questionSetId serial,
	questionId INTEGER,
	question TEXT,
	opt1 TEXT,
	opt2 TEXT,
	opt3 TEXT,
	opt4 TEXT,
	answer Text,
	PRIMARY KEY(questionSetId)
);
---------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS QuizType (
	quizId serial,
	questionSet INTEGER,
	quizName TEXT,
	PRIMARY KEY(quizId)
);
---------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS Session (
	sessionId serial,
	quizType INTEGER,
	winner INTEGER,
	Result TEXT,
	PRIMARY KEY(sessionId),
	FOREIGN KEY(quizType) REFERENCES QuizType (quizId) 
);
---------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS SessionQuestions (
	sessionQuestionId serial,
	questionId INTEGER,
	senderAnswer Text,
	receiverAnswer TEXT,
	FOREIGN KEY(sessionQuestionId) REFERENCES Session (sessionId) 
);

INSERT INTO employee (employee_ID, username, password, created_date, last_modified_date) VALUES
(1, 'thomas', '$2a$10$/2DA2514DAY7H81tK6tylOBhfpM/MndyEeAMnOCHtU1W7PDojYr/C', now(), now()),
(2, 'besmir', '$2a$10$/2DA2514DAY7H81tK6tylOBhfpM/MndyEeAMnOCHtU1W7PDojYr/C', now(), now()),
(3, 'bodhi', '$2a$10$/2DA2514DAY7H81tK6tylOBhfpM/MndyEeAMnOCHtU1W7PDojYr/C', now(), now()),
(4, 'bastien', '$2a$10$/2DA2514DAY7H81tK6tylOBhfpM/MndyEeAMnOCHtU1W7PDojYr/C', now(), now()),
(5, 'jdas', '$2a$10$/2DA2514DAY7H81tK6tylOBhfpM/MndyEeAMnOCHtU1W7PDojYr/C', now(), now());

INSERT INTO role(role_ID, role_type, created_date, employee_ID) VALUES
(1, 'ROLE_ADMIN', now(), 1),
(2, 'ROLE_USER', now(), 2),
(3, 'ROLE_USER', now(), 3),
(4, 'ROLE_USER', now(), 4),
(5, 'ROLE_USER', now(), 5);

INSERT INTO Quizzers(quizzerid, empid, lastplayed, totalplayed, totalwin) VALUES
(1, 5, now(), 0, 0);
