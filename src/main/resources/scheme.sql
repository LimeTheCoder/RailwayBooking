DROP SCHEMA IF EXISTS railway_booking;

CREATE DATABASE IF NOT EXISTS railway_booking DEFAULT CHARACTER SET utf8 ;
USE railway_booking;

DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
  email VARCHAR(50) NOT NULL,
  name VARCHAR(50) NOT NULL,
  surname VARCHAR(50) NOT NULL,
  phone VARCHAR(15) NOT NULL,
  role ENUM('USER', 'ADMIN') NOT NULL,
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

DROP TABLE IF EXISTS Invoices;

CREATE TABLE Invoices(
  id INT(10) NOT NULL AUTO_INCREMENT,
  passenger VARCHAR(50) NOT NULL,
  route INT(10) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT passenger_fk 
  	FOREIGN KEY(passenger) 
  	REFERENCES Users(email)
  	ON DELETE CASCADE
  	ON UPDATE CASCADE,
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

INSERT INTO Routes(departure_station, destination_station, departure_time, destination_time, train, price)
VALUES(1, 4, '2016-08-03 06:10:00', '2016-08-03 18:45:00', 'S6108-19', 500);
INSERT INTO Routes(departure_station, destination_station, departure_time, destination_time, train, price)
VALUES(3, 6, '2016-08-04 16:15:00', '2016-08-04 23:40:00', 'M6109-21', 125);
INSERT INTO Routes(departure_station, destination_station, departure_time, destination_time, train, price)
VALUES(1, 4, '2016-08-03 12:30:00', '2016-08-03 20:50:00', 'M6209-19', 250);
INSERT INTO Routes(departure_station, destination_station, departure_time, destination_time, train, price)
VALUES(5, 8, '2016-08-04 13:10:00', '2016-08-04 18:20:00', 'L6309-50', 90);

INSERT INTO Invoices(passenger, route) VALUES('test@gmail.com', 1);
INSERT INTO Invoices(passenger, route) VALUES('test2@gmail.com', 1);
INSERT INTO Invoices(passenger, route) VALUES('test3@gmail.com', 1);
INSERT INTO Invoices(passenger, route) VALUES('test2@gmail.com', 2);
INSERT INTO Invoices(passenger, route) VALUES('test3@gmail.com', 3);
