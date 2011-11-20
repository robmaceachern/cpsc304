--
-- Script to populate our database tables with some example entries
--
use crazycoollibrary;

--
-- Populate the book table with some example entries
--
insert into book(callNumber, isbn, title, mainAuthor, publisher, year)
values(1, 0470128720, 'Operating System Concepts', 'Abraham Silberschatz', 'Wiley', 2008),
      (2, 0495011665, 'Calculus: Early Transcendentals', 'James Stewart', 'Brooks Cole', 2007),
      (3, 0077211642, 'Fundamentals of Digital Logic', 'Stephen Brown', 'McGraw-Hill', 2007),
      (4, 0131687379, 'Fundamentals of Signals and Systems', 'Edward W. Kamen', 'Prentice Hall', 2006),
      (5, 0072465638, 'Database Management Systems', 'Raghu Ramakrishnan', 'McGraw-Hill', 2002);
  
--
-- Populate the bookcopy table with some example entries
--
insert into bookcopy(callNumber, copyNo, status)
values(1, 1, 'out'),
      (1, 2, 'in'),
      (2, 1, 'in'),
      (2, 2, 'out'),
      (3, 1, 'out'),
      (3, 2, 'in'),
      (4, 1, 'in'),
      (4, 2, 'in'),
      (5, 1, 'in'),
      (5, 2, 'in');
  
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
values(1, 1, 1, 1, 20111115, 20111129),
      (2, 2, 2, 2, 20111115, 20111129),
      (3, 3, 3, 1, 20111115, 20111129),
      (4, 3, 4, 2, 20111001, 20111021);
 
--
-- Populate the fine table with some example entries
-- 
insert into fine(fid, amount, issuedDate, paidDate, borid)
values(1, 10, 20111021, 20111022, 4);    

--
-- Populate the hasauthor table with some example entries
--
insert into hasauthor(author, callnumber)
values('Johannes Gehrke', 5),
      ('Bonnie S Heck', 4),
      ('Zvonko Vranesic', 3),
      ('Peter B. Galvin', 1),
      ('Greg Gagne', 1);
 
--
-- Populate the hassubject table with some example entries
-- 
insert into hassubject(subject, callnumber)
values('Database', 5),
      ('Signal Processing', 4),
      ('Digital Logic', 3),
      ('Calculus', 2),
      ('Operating Systems', 1);
 
--
-- Populate the holdrequest table with some example entries
-- 
insert into holdrequest(hid, bid, callNumber, issuedDate)
values(1, 1, 1, 20111029),
      (2, 2, 2, 20111030),
      (3, 3, 3, 20111031);
      
      
      