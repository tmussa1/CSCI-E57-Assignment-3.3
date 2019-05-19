CREATE TABLE companys_table(
  company_id        TEXT PRIMARY KEY NOT NULL,
  company_name TEXT NOT NULL,
  year_established  TEXT,
  number_of_employees      INT,
  capital   DOUBLE PRECISION );


INSERT INTO companys_table (company_id,  company_name , year_established, number_of_employees, capital)
VALUES ('2f341f84-accd-43a6-abbc-70d41de2c9d3', 'AT&T','1920', 5600, 6259.5);

INSERT INTO companys_table (company_id,  company_name , year_established, number_of_employees, capital)
VALUES ('562f5ed0-b0bf-40cf-85c1-e560631057b8', 'Verizon','1920', 9800, 5659.5);












