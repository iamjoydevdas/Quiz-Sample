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



INSERT INTO employee (employee_ID, username, password, created_date, last_modified_date) VALUES
(1, 'thomas', '$2a$10$tqMicHF44mIevvoRhr17A.m3jVLOfyYJA1XVPSn6t.1VhYfRRKAde', now(), now()),
(2, 'besmir', '$2a$10$/2DA2514DAY7H81tK6tylOBhfpM/MndyEeAMnOCHtU1W7PDojYr/C', now(), now()),
(3, 'bodhi', '$2a$10$NRxScDznkrwbpyAMEMv7De3iNrv2wuaQeMJBa0XjJHfhPUoYpvyVy', now(), now()),
(4, 'bastien', '$2a$10$BdoVxlwU6Hj6IrpwshfVW.4RGShq/x2BRrf5tIJ6XzlXbg.uKEXUe', now(), now());

INSERT INTO role(role_ID, role_type, created_date, employee_ID) VALUES
(1, 'ROLE_ADMIN', now(), 1),
(2, 'ROLE_USER', now(), 2),
(3, 'ROLE_USER', now(), 3),
(4, 'ROLE_USER', now(), 4);