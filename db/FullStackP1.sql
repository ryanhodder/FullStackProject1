DROP TABLE EMPLOYEE;
DROP TABLE MANAGER;
DROP TABLE REIMBURSEMENT;

CREATE TABLE EMPLOYEE(
    employee_id number(6) PRIMARY KEY,
    firstName VARCHAR2(255) NOT NULL,
    lastName VARCHAR2(255) NOT NULL,
    userName VARCHAR2(255) UNIQUE NOT NULL,
    e_password VARCHAR2(255) NOT NULL,
    email VARCHAR2(255)
);

CREATE TABLE MANAGER(
    manager_id number(6) PRIMARY KEY,
    firstName VARCHAR2(100) NOT NULL,
    lastName VARCHAR2(100) NOT NULL,
    userName VARCHAR2(100) UNIQUE NOT NULL,
    m_password VARCHAR2(100) NOT NULL
);

CREATE TABLE REIMBURSEMENT(
    reim_id NUMBER(6) PRIMARY KEY,
    amount NUMBER(10, 2) NOT NULL,
    pending NUMBER(1) NOT NULL,
    employee_id NUMBER(6) NOT NULL,
    CONSTRAINT employee_id FOREIGN KEY (employee_id) REFERENCES EMPLOYEE(employee_id)
);

CREATE SEQUENCE EMPLOYEE_ID_SEQ
    start with 1000
    increment by 2;
    
CREATE SEQUENCE MANAGER_ID_SEQ
    start with 1001
    increment by 2;
    
CREATE SEQUENCE REIMBURSEMENT_ID_SEQ
    start with 10
    increment by 1;
    
CREATE OR REPLACE TRIGGER addEmployeeIdTrig
BEFORE INSERT ON EMPLOYEE
FOR EACH ROW
BEGIN
    IF: new.employee_id IS NULL THEN
    SELECT EMPLOYEE_ID_SEQ.nextval INTO: new.employee_id from dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER addReimbursementTrig
BEFORE INSERT ON REIMBURSEMENT
FOR EACH ROW
BEGIN
    IF: new.reim_id IS NULL THEN
    SELECT REIMBURSEMENT_ID_SEQ.nextval INTO: new.reim_id from dual;
    END IF;
END;
/

CREATE OR REPLACE PROCEDURE addEmployee (firstName in VARCHAR2, lastName in VARCHAR2, userName in VARCHAR2, e_password in VARCHAR2, employee_id OUT NUMBER)
IS
BEGIN
    INSERT INTO EMPLOYEE (firstName, lastName, userName, e_password)
    VALUES (firstName, lastName, userName, e_password);
    employee_id := EMPLOYEE_ID_SEQ.currval;
    commit;
END;
/

CREATE OR REPLACE PROCEDURE addManager (firstName in VARCHAR2, lastName in VARCHAR2, userName in VARCHAR2, m_password in VARCHAR2, manager_id OUT NUMBER)
IS
BEGIN
    INSERT INTO MANAGER (firstName, lastName, userName, m_password)
    VALUES (firstName, lastName, userName, m_password);
    manager_id := MANAGER_ID_SEQ.currval;
    commit;
END;
/

CREATE OR REPLACE PROCEDURE addReim (amount in NUMBER, pending in NUMBER, employee_id in NUMBER, reim_id OUT NUMBER)
IS 
BEGIN
    INSERT INTO REIMBURSEMENT (amount, pending, employee_id)
    VALUES (amount, pending, employee_id);
    reim_id := REIMBURSEMENT_ID_SEQ.currval;
    commit;
END;
/

INSERT INTO EMPLOYEE VALUES (1, 'Ryan', 'Hodder', 'ryho', 'pass');
INSERT INTO EMPLOYEE VALUES (3, 'Began', 'Hoobler', 'beho', 'ssap');

SELECT * FROM EMPLOYEE;
SELECT * FROM EMPLOYEE WHERE userName = 'ryho' AND e_password = 'pass';

INSERT INTO MANAGER VALUES (2, 'Manny', 'Man', 'mannytheman', 'pass1');

SELECT * FROM MANAGER;

INSERT INTO REIMBURSEMENT VALUES(1, 1000.50, 1, 1);
INSERT INTO REIMBURSEMENT VALUES(2, 500, 0, 1);

SELECT * FROM REIMBURSEMENT;
SELECT * FROM REIMBURSEMENT WHERE employee_id = 1 and pending = 1;

commit;