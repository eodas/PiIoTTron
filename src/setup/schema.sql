-- Database Schema
-- Executive Order Corporation
-- Copyright - 1978, 2021: Executive Order Corporation, All Rights Reserved
--
-- This SQL script defines the tables, triggers and
-- views of the database. Sets the constraints, relationships
-- and rules of the database.
-- ---------------------------------------------------------------------------

PRAGMA foreign_keys=ON;

-- Reset:
-- Drops all the tables, triggers and views in the database
-- to prepare for their recreation.
-- ------------------------------------------------------------

DROP TABLE   IF EXISTS Device;
DROP TABLE   IF EXISTS Event;
DROP TABLE   IF EXISTS User;

-- Table Creation
-- ------------------------------------------------------------

CREATE TABLE Device (
    id   	VARCHAR(25)		NOT NULL,   -- maybe alphanumeric
    name   	VARCHAR(25),
	purpose	VARCHAR(25),
	description VARCHAR(25),
	location VARCHAR(25),
	ipaddress VARCHAR(25),
	model	VARCHAR(25),
	process	VARCHAR(25),
	status	VARCHAR(25),
	position VARCHAR(25),
	contact	VARCHAR(25),
	category VARCHAR(25),
	message	VARCHAR(25),
	lastUpdate VARCHAR(25),
	disabled VARCHAR(1),
	PRIMARY KEY(id ASC)
);

CREATE TABLE Event (
    pk		INTEGER PRIMARY KEY AUTOINCREMENT, 
    id   	VARCHAR(10),   -- maybe alphanumeric
	name	VARCHAR(25),
	events	VARCHAR(25),
	description VARCHAR(25),
	process	VARCHAR(25),
	protocols VARCHAR(25),
	serverTime VARCHAR(25),
	deviceTime VARCHAR(25),
	fixTime	VARCHAR(25),
	outdated VARCHAR(1),
	valid 	VARCHAR(1),
	lat		REAL,
	lon		REAL,
	altitude REAL,
	speed	REAL,
	course 	REAL,
	address	VARCHAR(25),
	accuracy REAL,
	bearing  REAL,
	network VARCHAR(25),
	satellites REAL,
	hdop	REAL,
	cell	VARCHAR(25),
	wifi	VARCHAR(25),
	battery	REAL,
	message	VARCHAR(25),
	temps 	REAL,
	ir_temp	REAL,
	humidity REAL,
	mbar 	REAL,
	accel_x REAL,
	accel_y REAL,
	accel_z REAL,
	gyro_x 	REAL,
	gyro_y	REAL,
	gyro_z 	REAL,
	magnet_x REAL,
	magnet_y REAL,
	magnet_z REAL,
	light 	REAL,
	keypress REAL,
	alarm	VARCHAR(25),
	distance REAL,
	totalDistance REAL,
	agentCount REAL,
	motion	VARCHAR(1)
);
    
CREATE TABLE User (
	login	VARCHAR(25) 	NOT NULL,   -- maybe alphanumeric
	name	VARCHAR(25),
	email	VARCHAR(25),
	phone	VARCHAR(25),
	passwords VARCHAR(25),
	readonly VARCHAR(1),
	disabled VARCHAR(1),
	administrator VARCHAR(1),
	deviceReadonly VARCHAR(1),
	PRIMARY KEY(login ASC)
);
