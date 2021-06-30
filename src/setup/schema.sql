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
	typeArn	VARCHAR(25),
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
    id   	VARCHAR(10)		NOT NULL,   -- maybe alphanumeric
	name	VARCHAR(25),
	event	VARCHAR(25),
	description VARCHAR(25),
	process	VARCHAR(25),
	protocol VARCHAR(25),
	serverTime VARCHAR(25),
	deviceTime VARCHAR(25),
	fixTime	VARCHAR(25),
	outdated VARCHAR(1),
	valid 	VARCHAR(1),
	lat		INT,
	lon		INT,
	altitude INT,
	speed	INT,
	course 	INT,
	address	VARCHAR(25),
	accuracy INT,
	bearing  INT,
	network VARCHAR(25),
	hdop	INT,
	cell	VARCHAR(25),
	wifi	VARCHAR(25),
	battery	INT,
	message	VARCHAR(25),
	temp 	INT,
	ir_temp	INT,
	humidity INT,
	mbar 	INT,
	accel_x INT,
	accel_y INT,
	accel_z INT,
	gyro_x 	INT,
	gyro_y	INT,
	gyro_z 	INT,
	magnet_x INT,
	magnet_y INT,
	magnet_z INT,
	light 	INT,
	keypress INT,
	alarm	VARCHAR(25),
	distance INT,
	totalDistance INT,
	agentCount INT,
	motion	VARCHAR(1),
	PRIMARY KEY(id ASC)
);
    
CREATE TABLE User (
	login	VARCHAR(25) 	NOT NULL,   -- maybe alphanumeric
	name	VARCHAR(25),
	email	VARCHAR(25),
	phone	VARCHAR(25),
	password VARCHAR(25),
	readonly VARCHAR(1),
	administrator VARCHAR(1),
	disabled VARCHAR(1),
	deviceLimit INT,
	userLimit INT,
	deviceReadonly VARCHAR(1),
	limitCommands VARCHAR(1),
	PRIMARY KEY(login ASC)
);
