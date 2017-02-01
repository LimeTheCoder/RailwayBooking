DROP SCHEMA IF EXISTS test_booking;

CREATE DATABASE IF NOT EXISTS test_booking DEFAULT CHARACTER SET utf8 ;
USE test_booking;

DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
  email VARCHAR(50) NOT NULL,
  name VARCHAR(50) NOT NULL,
  surname VARCHAR(50) NOT NULL,
  phone VARCHAR(15) NOT NULL,
  role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
  password VARCHAR(60) NOT NULL,
  PRIMARY KEY (email))
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS Stations;

CREATE TABLE Stations(
  id INT(10) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  city VARCHAR(50) NOT NULL,
  country VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS Trains;

CREATE TABLE Trains(
  serial_no VARCHAR(10) NOT NULL,
  capacity INT(10) NOT NULL DEFAULT 0,
  PRIMARY KEY (serial_no))

  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS Routes;

CREATE TABLE Routes(
  id INT(10) NOT NULL AUTO_INCREMENT,
  departure_station INT(10) NOT NULL,
  destination_station INT(10) NOT NULL,
  departure_time TIMESTAMP NOT NULL DEFAULT now(),
  destination_time TIMESTAMP NOT NULL DEFAULT now(),
  train VARCHAR(10) NOT NULL UNIQUE,
  reserved_cnt INT(10) NOT NULL DEFAULT 0,
  price INT(10) NOT NULL,
  PRIMARY KEY (id),
  INDEX dep_dest_idx(departure_station, destination_station),
  CONSTRAINT departure_station_fk
  	FOREIGN KEY(departure_station)
  	REFERENCES Stations(id)
  	ON DELETE CASCADE,
  CONSTRAINT destination_station_fk
  	FOREIGN KEY(destination_station)
  	REFERENCES Stations(id)
  	ON DELETE CASCADE,
  CONSTRAINT train_fk
  	FOREIGN KEY(train)
  	REFERENCES Trains(serial_no)
  	ON DELETE CASCADE
  	ON UPDATE CASCADE)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS Requests;

CREATE TABLE Requests(
  id INT(10) NOT NULL AUTO_INCREMENT,
  passenger VARCHAR(50) NOT NULL,
  departure INT(10) NOT NULL,
  destination INT(10) NOT NULL,
  departure_time TIMESTAMP NOT NULL DEFAULT now(),
  creation_time TIMESTAMP NOT NULL DEFAULT now(),
  result_cnt INT(10) NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  CONSTRAINT passenger_fk
  	FOREIGN KEY(passenger)
  	REFERENCES Users(email)
  	ON DELETE CASCADE
  	ON UPDATE CASCADE,
  CONSTRAINT departure_fk
  	FOREIGN KEY(departure)
  	REFERENCES Stations(id)
  	ON DELETE CASCADE,
  CONSTRAINT destination_fk
  	FOREIGN KEY(destination)
  	REFERENCES Stations(id)
  	ON DELETE CASCADE)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS Invoices;

CREATE TABLE Invoices(
  id INT(10) NOT NULL AUTO_INCREMENT,
  request INT(10) NOT NULL UNIQUE,
  route INT(10) NOT NULL,
  status ENUM('PAID', 'PENDING', 'REJECTED') NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (id),
  CONSTRAINT request_fk
  	FOREIGN KEY(request)
  	REFERENCES Requests(id)
  	ON DELETE CASCADE,
  CONSTRAINT route_fk
  	FOREIGN KEY(route)
  	REFERENCES Routes(id)
  	ON DELETE CASCADE)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


INSERT INTO Users(email, password, name, surname, phone, role)
	VALUES('test@gmail.com', 'password',
	'John', 'Smitt', '380562517296', 'USER');
INSERT INTO Users(email, password, name, surname, phone, role)
	VALUES('test2@gmail.com', 'password',
	'Lesley', 'Lewis', '380944656193', 'USER');
INSERT INTO Users(email, password, name, surname, phone, role)
	VALUES('admin@gmail.com', 'password',
	'Jessy', 'Williams', '380665536386', 'ADMIN');

INSERT INTO Stations(name, city, country) VALUES('South', 'Kyiv', 'Ukraine');
INSERT INTO Stations(name, city, country) VALUES('Main', 'Lviv', 'Ukraine');
INSERT INTO Stations(name, city, country) VALUES('Central', 'Odessa', 'Ukraine');
INSERT INTO Stations(name, city, country) VALUES('Main', 'Kharkiv', 'Ukraine');

INSERT INTO Trains(serial_no, capacity) VALUES('S6108-19', 3);
INSERT INTO Trains(serial_no, capacity) VALUES('M6209-19', 13);

INSERT INTO Routes(departure_station, destination_station, departure_time, destination_time, train, price)
VALUES(1, 4, '2017-02-08 06:10:00', '2017-02-08 18:45:00', 'S6108-19', 500);
INSERT INTO Routes(departure_station, destination_station, departure_time, destination_time, train, price)
VALUES(2, 3, '2017-02-04 13:10:00', '2017-02-04 18:20:00', 'M6209-19', 90);

INSERT INTO Requests(passenger, departure, destination, departure_time, result_cnt)
VALUES('test@gmail.com', 1, 4, '2017-02-06 00:00:00', 1);
INSERT INTO Requests(passenger, departure, destination, departure_time, result_cnt)
VALUES('test2@gmail.com', 1, 4, '2017-02-04 00:00:00', 1);
INSERT INTO Requests(passenger, departure, destination, departure_time, result_cnt)
VALUES('test@gmail.com', 2, 3, '2017-02-04 00:00:00', 1);

INSERT INTO Invoices(request, route, status) VALUES(1, 1, 'PENDING');
INSERT INTO Invoices(request, route, status) VALUES(2, 1, 'PAID');
INSERT INTO Invoices(request, route, status) VALUES(3, 2, 'REJECTED');