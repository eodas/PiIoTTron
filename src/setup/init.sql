-- Database Initialization
-- Executive Order Corporation
-- Copyright - 1978, 2021: Executive Order Corporation, All Rights Reserved
-- Populate the database with data.
-- ---------------------------------------------------------------------------

INSERT INTO Device(id, name) VALUES 
	("100111", "Arduino Tron IoT"), 
	("100222", "Temperature-Humidity"), 
	("100333", "Door Lock IoT-MCU"), 
	("100444", "Arduino IoT-SensorTag"), 
	("100555", "Arduino Dash Button"), 
	("100666", "Door Open Sensor ESP01"), 
	("100777", "Light Module IoT-MCU"), 
	("100888", "Arduino Tron IoT Display"); 

INSERT INTO User(login, name) VALUES
    ("1234", "Steven Woodward"),
    ("1111", "John Thompson"),
    ("2222", "Mark McLaren"),
    ("3333", "Alan Jones"),
    ("4444", "Ernie Smith"),
    ("5555", "Dalton Eves"),
    ("6666", "Michael Right");
