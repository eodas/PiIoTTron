-- Database Initialization
-- Executive Order Corporation
-- Copyright - 1978, 2021: Executive Order Corporation, All Rights Reserved
-- Populate the database with data.
-- ---------------------------------------------------------------------------

INSERT INTO Device(id, name, process) VALUES 
	("100111", "Arduino Tron IoT", ""),
	("100222", "Temperature-Humidity", ""),
	("100333", "Door Lock IoT-MCU", ""),
	("100444", "TISensorTag GPS Environment", "com.TISensorTagEnvironment"),
	("100555", "Arduino Dash Button", ""),
	("100666", "Door Open Sensor ESP01", ""),
	("100777", "Light Module IoT-MCU", ""),
	("100888", "Arduino Tron IoT Display", ""),
  	("100910", "Jarvis Pi IoT Tron", "com.JarvisPiIoTTron"),
  	("100920", "EOSpy IoT GPS Position", "com.GPSPositionTron"),
  	("100930", "IoT-TISensorTag GPS Environment", "com.TISensorTagEnvironment");

INSERT INTO User(login, name) VALUES
    ("1234", "Steven Woodward"),
    ("1111", "John Thompson"),
    ("2222", "Mark McLaren"),
    ("3333", "Alan Jones"),
    ("4444", "Ernie Smith"),
    ("5555", "Dalton Eves"),
    ("6666", "Michael Right");
