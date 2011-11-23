--
-- Script to populate our database tables with some example entries
--

--
-- Populate the book table with some example entries
--
insert into book(isbn, title, mainAuthor, publisher, year)
values(0470128720, 'Operating System Concepts', 'Abraham Silberschatz', 'Wiley', 2008),
      (0495011665, 'Calculus: Early Transcendentals', 'James Stewart', 'Brooks Cole', 2007),
      (0077211642, 'Fundamentals of Digital Logic', 'Stephen Brown', 'McGraw-Hill', 2007),
      (0131687379, 'Fundamentals of Signals and Systems', 'Edward W. Kamen', 'Prentice Hall', 2006),
      (0072465638, 'Database Management Systems', 'Raghu Ramakrishnan', 'McGraw-Hill', 2002);
  
--
-- Populate the bookcopy table with some example entries
--
insert into bookcopy(callNumber, copyNo, status)
values(10001, 1, 'out'),
      (10001, 2, 'in'),
      (10002, 1, 'in'),
      (10002, 2, 'out'),
      (10003, 1, 'out'),
      (10003, 2, 'in'),
      (10004, 1, 'in'),
      (10004, 2, 'in'),
      (10005, 1, 'in'),
      (10005, 2, 'in');
  
--
-- Populate the borrowerType table with some example entries
--
insert into borrowerType(btype, bookTimeLimit)
values('student', '2'),
      ('faculty', '12'),
      ('staff', '6');
      
--
-- Populate the borrower table with some example entries
--     
insert into borrower(bid, password, name, address, phone, emailAddress, sinOrStNo, expiryDate, btype)
values(1, 'password1', 'Riley Chang', '123 Oak Street', 1234567, 'riley@cpsc304.com', 1234567, 20130101, 'staff'),
      (2, 'password2', 'Rob MacEachern', '234 Main Street', 2345678, 'rob@cpsc304.com', 2345678, 20130101, 'faculty'),
      (3, 'password3', 'Robert Woff', '123 Maple Street', 3456789, 'robert@cpsc304.com', 3456789, 20130101, 'student');
 
--
-- Populate the borrowing table with some example entries
-- 
insert into borrowing(borid, bid, callNumber, copyNo, outDate, inDate)
values(1, 1, 10001, 1, 20111115, 20111129),
      (2, 2, 10002, 2, 20111115, 20111129),
      (3, 3, 10003, 1, 20111115, 20111129),
      (4, 3, 10004, 2, 20111001, 20111021);
 
--
-- Populate the fine table with some example entries
-- 
insert into fine(fid, amount, issuedDate, paidDate, borid)
values(1, 10, 20111021, 20111022, 4);    

--
-- Populate the hasauthor table with some example entries
--
insert into hasauthor(author, callnumber)
values('Johannes Gehrke', 10005),
      ('Bonnie S Heck', 10004),
      ('Zvonko Vranesic', 10003),
      ('Peter B. Galvin', 10001),
      ('Greg Gagne', 10001);
 
--
-- Populate the hassubject table with some example entries
-- 
insert into hassubject(subject, callnumber)
values('Database', 10005),
      ('Signal Processing', 10004),
      ('Digital Logic', 10003),
      ('Calculus', 10002),
      ('Operating Systems', 10001);
 
--
-- Populate the holdrequest table with some example entries
-- 
insert into holdrequest(hid, bid, callNumber, issuedDate)
values(1, 1, 10001, 20111029),
      (2, 2, 10002, 20111030),
      (3, 3, 10003, 20111031);
      
      
      