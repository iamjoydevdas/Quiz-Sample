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
	quizzer_ID serial,
	emp_ID INTEGER,
	last_Played DATE,
	total_Played INTEGER,
	total_Win INTEGER,
	quizzer_status INTEGER,
	PRIMARY KEY(quizzer_ID),
	FOREIGN KEY(emp_ID) REFERENCES employee (employee_ID) 
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
	answer Text
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

INSERT INTO Quizzers(quizzer_ID, emp_ID, last_Played, total_Played, total_Win, quizzer_status) VALUES
(1, 5, now(), 0, 0, 1);

INSERT INTO QuizType(quizId, questionSet, quizName) values(1, 1, 'Java');
INSERT INTO QuizType(quizId, questionSet, quizName) values(2, 2, 'Vaadin');
INSERT INTO QuizType(quizId, questionSet, quizName) values(3, 3, 'Oracle');