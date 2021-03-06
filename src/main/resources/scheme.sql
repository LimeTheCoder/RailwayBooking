DROP SCHEMA IF EXISTS railway_booking;

CREATE DATABASE IF NOT EXISTS railway_booking DEFAULT CHARACTER SET utf8 ;
USE railway_booking;

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
	VALUES('test@gmail.com', '$2a$10$.jtJduq/M3xeePiuWUhytOCH4u6WYZiLKJnVBWNpPQa4SGHjck8bC',
	'John', 'Smitt', '380562517296', 'USER');
INSERT INTO Users(email, password, name, surname, phone, role) 
	VALUES('test2@gmail.com', '$2a$10$zaLSnXWi7ctehTyMk3VCTO6CUD4O0w4QxRh5nW99oHB/G5uu1TcmK',
	'Олег', 'Іванов', '380944656193', 'USER');
INSERT INTO Users(email, password, name, surname, phone, role) 
	VALUES('test3@gmail.com', '$2a$10$BcKQBUxTBgAypMzwykCWyeJhkWhxvF2ayDj.69ZNVsdFe5xQTwjoC',
	'Катерина', 'Михайленко', '380922836292', 'USER');
INSERT INTO Users(email, password, name, surname, phone, role) 
	VALUES('test4@gmail.com', '$2a$10$sCacNCYJsK5KA6n7OgJKgebPdPSFelRvN5Ybw5usfE4/aB5GQFZOq',
	'Lesley', 'Cole', '380962583293', 'USER');
INSERT INTO Users(email, password, name, surname, phone, role)
	VALUES('admin@gmail.com', '$2a$10$Vm9Ol4p/GSW7KWM96ujHHuK1P0Fbo/mm6gKUSwLSuqMXGeSlpJUhO',
	'Jessy', 'Williams', '380665536386', 'ADMIN');

INSERT INTO Stations(name, city, country) VALUES('Південний', 'Київ', 'Україна');
INSERT INTO Stations(name, city, country) VALUES('Центральний', 'Київ', 'Україна');
INSERT INTO Stations(name, city, country) VALUES('Приміський', 'Київ', 'Україна');
INSERT INTO Stations(name, city, country) VALUES('Головний', 'Львів', 'Україна');
INSERT INTO Stations(name, city, country) VALUES('Центральний', 'Луцьк', 'Україна');
INSERT INTO Stations(name, city, country) VALUES('Центральний', 'Одеса', 'Україна');
INSERT INTO Stations(name, city, country) VALUES('Центральний', 'Черкаси', 'Україна');
INSERT INTO Stations(name, city, country) VALUES('Головний', 'Харків', 'Україна');
INSERT INTO Stations(name, city, country) VALUES('Центральний', 'Сімферополь', 'Україна');

INSERT INTO Trains(serial_no, capacity) VALUES('M6109-21', 25);
INSERT INTO Trains(serial_no, capacity) VALUES('S6108-19', 3);
INSERT INTO Trains(serial_no, capacity) VALUES('M6209-19', 13);
INSERT INTO Trains(serial_no, capacity) VALUES('L6309-50', 50);
INSERT INTO Trains(serial_no, capacity) VALUES('K7777-50', 50);
INSERT INTO Trains(serial_no, capacity) VALUES('K8888-10', 10);
INSERT INTO Trains(serial_no, capacity) VALUES('K4444-33', 33);

INSERT INTO Routes(departure_station, destination_station, departure_time, destination_time, train, price, reserved_cnt)
VALUES(1, 4, '2017-02-08 06:10:00', '2017-02-08 18:45:00', 'S6108-19', 500, 2);
INSERT INTO Routes(departure_station, destination_station, departure_time, destination_time, train, price, reserved_cnt)
VALUES(3, 6, '2017-02-04 16:15:00', '2017-02-04 23:40:00', 'M6109-21', 125, 1);
INSERT INTO Routes(departure_station, destination_station, departure_time, destination_time, train, price, reserved_cnt)
VALUES(1, 4, '2017-02-05 12:30:00', '2017-02-05 20:50:00', 'M6209-19', 250, 0);
INSERT INTO Routes(departure_station, destination_station, departure_time, destination_time, train, price, reserved_cnt)
VALUES(5, 8, '2017-02-04 13:10:00', '2017-02-04 18:20:00', 'L6309-50', 90, 1);

INSERT INTO Requests(passenger, departure, destination, departure_time, result_cnt)
VALUES('test@gmail.com', 1, 4, '2017-02-06 00:00:00', 1);
INSERT INTO Requests(passenger, departure, destination, departure_time, result_cnt)
VALUES('test3@gmail.com', 1, 4, '2017-02-04 00:00:00', 2);
INSERT INTO Requests(passenger, departure, destination, departure_time, result_cnt)
VALUES('test2@gmail.com', 3, 6, '2017-02-04 00:00:00', 1);
INSERT INTO Requests(passenger, departure, destination, departure_time, result_cnt)
VALUES('test3@gmail.com', 5, 8, '2017-02-04 00:00:00', 1);

INSERT INTO Invoices(request, route, status) VALUES(1, 1, 'PENDING');
INSERT INTO Invoices(request, route, status) VALUES(2, 1, 'PAID');
INSERT INTO Invoices(request, route, status) VALUES(3, 2, 'PENDING');
INSERT INTO Invoices(request, route, status) VALUES(4, 4, 'PENDING');

ALTER TABLE Stations
  ADD CONSTRAINT uq_stations UNIQUE(name, city, country);