package com.piiottron.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An event informing of a state change due to some operation or activity.
 * 
 * IoT devices send progress events informing about a state change due to some
 * operation or activity. Each message as it passes to or from your devices
 * through the message broker and jBPM - rules engine.
 * 
 * With IoT alarms and metrics using CloudWatch You can monitor IoT device using
 * CloudWatch, which collects and processes raw data from IoT into readable,
 * near real-time metrics.
 */
public class Event {

	private final Logger logger = LoggerFactory.getLogger(Event.class);
	                                   
	private final String DateFormat = "yyyy-MM-dd HH:mm:ss.ms"; // MM/dd/yyyy HH:mm:ss
	
	public Map<String, String> map = new HashMap<>();

	public String id;
	public String name;
	public String event;
	public String description;
	public String process;

	public String protocol; // position
	public String serverTime; // position
	public String deviceTime; // position
	public String fixTime; // position
	public boolean outdated; // position
	public boolean valid; // position
	public double lat; // position
	public double lon; // position
	// meta filed location setLat(Double.parseDouble(location[0])); split(","); setLon(Double.parseDouble(location[1]));
	public double altitude; // position - value in meters
	public double speed; // position - value in mph
	public double course; // position
	public String address; // position
	public double accuracy; // position
	public double bearing; // position
	public String network; // position
	
	public double satellites;
	public double hdop;
	public String cell;
	public String wifi;
	public double battery;
	public String message;
	
	public double temp;
	public double ir_temp;
	public double humidity;
	public double mbar;
	public double accel_x;
	public double accel_y;
	public double accel_z;
	public double gyro_x;
	public double gyro_y;
	public double gyro_z;
	public double magnet_x;
	public double magnet_y;
	public double magnet_z;
	
	public double light;
	public double keypress;
	public String alarm;
	public double distance;
	public double totalDistance;
	public double agentCount;
	public boolean motion;

	public static final String ALL_EVENTS = "allEvents";

	public static final String TYPE_COMMAND_RESULT = "commandResult";

	public static final String TYPE_DEVICE_ONLINE = "deviceOnline";
	public static final String TYPE_DEVICE_UNKNOWN = "deviceUnknown";
	public static final String TYPE_DEVICE_OFFLINE = "deviceOffline";

	public static final String TYPE_DEVICE_MOVING = "deviceMoving";
	public static final String TYPE_DEVICE_STOPPED = "deviceStopped";

	public static final String TYPE_DEVICE_OVERSPEED = "deviceOverspeed";
	public static final String TYPE_DEVICE_FUEL_DROP = "deviceFuelDrop";

	public static final String TYPE_GEOFENCE_ENTER = "geofenceEnter";
	public static final String TYPE_GEOFENCE_EXIT = "geofenceExit";

	public static final String TYPE_ALARM = "alarm";

	public static final String TYPE_IGNITION_ON = "ignitionOn";
	public static final String TYPE_IGNITION_OFF = "ignitionOff";

	public static final String TYPE_MAINTENANCE = "maintenance";

	public static final String TYPE_TEXT_MESSAGE = "textMessage";

	public static final String TYPE_DRIVER_CHANGED = "driverChanged";

	// eospy
	public static final String TYPE_KEYPRESS_1 = "keyPress_1";
	public static final String TYPE_KEYPRESS_2 = "keyPress_2";
	public static final String TYPE_REED_RELAY = "reedRelay";
	public static final String TYPE_PROXIMITY = "Proximity";

	public static final String KEY_ORIGINAL = "raw";
	public static final String KEY_INDEX = "index";
	public static final String KEY_HDOP = "hdop";
	public static final String KEY_VDOP = "vdop";
	public static final String KEY_PDOP = "pdop";
	public static final String KEY_SATELLITES = "sat"; // in use
	public static final String KEY_SATELLITES_VISIBLE = "satVisible";
	public static final String KEY_RSSI = "rssi";
	public static final String KEY_GPS = "gps";
	public static final String KEY_ROAMING = "roaming";
	public static final String KEY_EVENT = "event";
	public static final String KEY_ALARM = "alarm";
	public static final String KEY_STATUS = "status";
	public static final String KEY_ODOMETER = "odometer"; // meters
	public static final String KEY_ODOMETER_SERVICE = "serviceOdometer"; // meters
	public static final String KEY_ODOMETER_TRIP = "tripOdometer"; // meters
	public static final String KEY_HOURS = "hours";
	public static final String KEY_STEPS = "steps";
	public static final String KEY_HEART_RATE = "heartRate";
	public static final String KEY_INPUT = "input";
	public static final String KEY_OUTPUT = "output";
	public static final String KEY_IMAGE = "image";
	public static final String KEY_VIDEO = "video";
	public static final String KEY_AUDIO = "audio";

	// The units for the below four KEYs currently vary.
	// The preferred units of measure are specified in the comment for each.
	public static final String KEY_POWER = "power"; // volts
	public static final String KEY_BATTERY = "battery"; // volts
	public static final String KEY_BATTERY_LEVEL = "batteryLevel"; // percentage
	public static final String KEY_FUEL_LEVEL = "fuel"; // liters
	public static final String KEY_FUEL_USED = "fuelUsed"; // liters
	public static final String KEY_FUEL_CONSUMPTION = "fuelConsumption"; // liters/hour

	public static final String KEY_VERSION_FW = "versionFw";
	public static final String KEY_VERSION_HW = "versionHw";
	public static final String KEY_TYPE = "type";
	public static final String KEY_IGNITION = "ignition";
	public static final String KEY_FLAGS = "flags";
	public static final String KEY_ANTENNA = "antenna";
	public static final String KEY_CHARGE = "charge";
	public static final String KEY_IP = "ip";
	public static final String KEY_ARCHIVE = "archive";
	public static final String KEY_DISTANCE = "distance"; // meters
	public static final String KEY_TOTAL_DISTANCE = "totalDistance"; // meters
	public static final String KEY_RPM = "rpm";
	public static final String KEY_VIN = "vin";
	public static final String KEY_APPROXIMATE = "approximate";
	public static final String KEY_THROTTLE = "throttle";
	public static final String KEY_MOTION = "motion";
	public static final String KEY_ARMED = "armed";
	public static final String KEY_GEOFENCE = "geofence";
	public static final String KEY_ACCELERATION = "acceleration";
	public static final String KEY_DEVICE_TEMP = "deviceTemp"; // celsius
	public static final String KEY_COOLANT_TEMP = "coolantTemp"; // celsius
	public static final String KEY_ENGINE_LOAD = "engineLoad";
	public static final String KEY_OPERATOR = "operator";
	public static final String KEY_COMMAND = "command";
	public static final String KEY_BLOCKED = "blocked";
	public static final String KEY_DOOR = "door";
	public static final String KEY_AXLE_WEIGHT = "axleWeight";
	public static final String KEY_G_SENSOR = "gSensor";
	public static final String KEY_ICCID = "iccid";
	public static final String KEY_PHONE = "phone";

	public static final String KEY_DTCS = "dtcs";
	public static final String KEY_OBD_SPEED = "obdSpeed"; // knots
	public static final String KEY_OBD_ODOMETER = "obdOdometer"; // meters

	public static final String KEY_RESULT = "result";
	public static final String KEY_DRIVER_UNIQUE_ID = "driverUniqueId";

	// Start with 1 not 0
	public static final String PREFIX_TEMP = "temp";
	public static final String PREFIX_ADC = "adc";
	public static final String PREFIX_IO = "io";
	public static final String PREFIX_COUNT = "count";
	public static final String PREFIX_IN = "in";
	public static final String PREFIX_OUT = "out";

	public static final String ALARM_GENERAL = "general";
	public static final String ALARM_SOS = "sos";
	public static final String ALARM_VIBRATION = "vibration";
	public static final String ALARM_MOVEMENT = "movement";
	public static final String ALARM_LOW_SPEED = "lowspeed";
	public static final String ALARM_OVERSPEED = "overspeed";
	public static final String ALARM_FALL_DOWN = "fallDown";
	public static final String ALARM_LOW_POWER = "lowPower";
	public static final String ALARM_LOW_BATTERY = "lowBattery";
	public static final String ALARM_FAULT = "fault";
	public static final String ALARM_POWER_OFF = "powerOff";
	public static final String ALARM_POWER_ON = "powerOn";
	public static final String ALARM_DOOR = "door";
	public static final String ALARM_LOCK = "lock";
	public static final String ALARM_UNLOCK = "unlock";
	public static final String ALARM_GEOFENCE = "geofence";
	public static final String ALARM_GEOFENCE_ENTER = "geofenceEnter";
	public static final String ALARM_GEOFENCE_EXIT = "geofenceExit";
	public static final String ALARM_GPS_ANTENNA_CUT = "gpsAntennaCut";
	public static final String ALARM_ACCIDENT = "accident";
	public static final String ALARM_TOW = "tow";
	public static final String ALARM_IDLE = "idle";
	public static final String ALARM_HIGH_RPM = "highRpm";
	public static final String ALARM_ACCELERATION = "hardAcceleration";
	public static final String ALARM_BRAKING = "hardBraking";
	public static final String ALARM_CORNERING = "hardCornering";
	public static final String ALARM_LANE_CHANGE = "laneChange";
	public static final String ALARM_FATIGUE_DRIVING = "fatigueDriving";
	public static final String ALARM_POWER_CUT = "powerCut";
	public static final String ALARM_POWER_RESTORED = "powerRestored";
	public static final String ALARM_JAMMING = "jamming";
	public static final String ALARM_TEMPERATURE = "temperature";
	public static final String ALARM_PARKING = "parking";
	public static final String ALARM_SHOCK = "shock";
	public static final String ALARM_BONNET = "bonnet";
	public static final String ALARM_FOOT_BRAKE = "footBrake";
	public static final String ALARM_FUEL_LEAK = "fuelLeak";
	public static final String ALARM_TAMPERING = "tampering";
	public static final String ALARM_REMOVING = "removing";

	// eospy ti sensor tag
	public static final String KEY_TEMP = "temp";
	public static final String KEY_IR_TEMP = "ir_temp";
	public static final String KEY_HUMIDITY = "humidity";
	public static final String KEY_MBAR = "mbar";

	public static final String KEY_ACCEL_X = "accel_x";
	public static final String KEY_ACCEL_Y = "accel_y";
	public static final String KEY_ACCEL_Z = "accel_z";
	public static final String KEY_GYRO_X = "gyro_x";
	public static final String KEY_GYRO_Y = "gyro_y";
	public static final String KEY_GYRO_Z = "gyro_z";
	public static final String KEY_MAGNET_X = "magnet_x";
	public static final String KEY_MAGNET_Y = "magnet_y";
	public static final String KEY_MAGNET_Z = "magnet_z";

	public static final String KEY_LIGHT = "light";
	public static final String KEY_KEYPRESS = "keypress";

	public Event() {
		setServerTime(new SimpleDateFormat(DateFormat).format(new Date()));
	}

    public Event(String id, String name, String event, String description, String process,
    			 String protocol, String serverTime, String deviceTime, String fixTime, boolean outdated, boolean valid, double lat, double lon,
    			 double altitude, double speed, double course, String address, double accuracy, double bearing, String network, double satellites,
    			 double hdop, String cell, String wifi, double battery, String message, double temp, double ir_temp, double humidity, double mbar,
    			 double accel_x, double accel_y, double accel_z, double gyro_x, double gyro_y, double gyro_z, double magnet_x, double magnet_y, double magnet_z,
    			 double light, double keypress, String alarm, double distance, double totalDistance, double agentCount, boolean motion) {

    	this.id = id;
    	this.name = name;
    	this.event = event;
    	this.description = description;
    	this.process = process;
    	
    	this.protocol = protocol; 
    	this.serverTime = serverTime; 
    	this.deviceTime = deviceTime; 
    	this.fixTime = fixTime; 
    	this.outdated = outdated; 
    	this.valid = valid;
    	this.lat = lat;
    	this.lon = lon;

    	this.altitude = altitude;
    	this.speed = speed;
    	this.course = course;
    	this.address = address;
    	this.accuracy = accuracy;
    	this.bearing = bearing;
    	this.network = network;
    	
    	this.satellites = satellites;
    	this.hdop = hdop;
    	this.cell = cell;
    	this.wifi = wifi;
    	this.battery = battery;
    	this.message = message;
    	
    	this.temp = temp;
    	this.ir_temp = ir_temp;
    	this.humidity = humidity;
    	this.mbar = mbar;
    	this.accel_x = accel_x;
    	this.accel_y = accel_y;
    	this.accel_z = accel_z;
    	this.gyro_x = gyro_x;
    	this.gyro_y = gyro_y;
    	this.gyro_z = gyro_z;
    	this.magnet_x = magnet_x;
    	this.magnet_y = magnet_y;
    	this.magnet_z = magnet_z;
    	
    	this.light = light;
    	this.keypress = keypress;
    	this.alarm = alarm;
    	this.distance = distance;
    	this.totalDistance = totalDistance;
    	this.agentCount = agentCount;
    	this.motion = motion;
    }

	public void add(String key, String value) {
		map.put(key, value);
		EventParser(key, value);
	}

	public int mapSize() {
		return map.size();
	}

	// Clear all values.
	public void mapClear() {
		map.clear();
	}

	public String search(String searchKey) {
		if (map.containsKey(searchKey)) {
			return map.get(searchKey);
		} else {
			return "";
		}
	}

	// Iterate over objects, using the keySet method.
	public void iterate() {
		for (String key : map.keySet())
			System.out.println(key + " - " + map.get(key));
		System.out.println();
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getServerTime() {
		return serverTime;
	}

	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}

	public String getDeviceTime() {
		return deviceTime;
	}

	public void setDeviceTime(String deviceTime) {
		this.deviceTime = deviceTime;
	}

	public String getFixTime() {
		return fixTime;
	}

	public void setFixTime(String fixTime) {
		this.fixTime = fixTime;
	}

	public boolean isOutdated() {
		return outdated;
	}

	public void setOutdated(boolean outdated) {
		this.outdated = outdated;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getCourse() {
		return course;
	}

	public void setCourse(double course) {
		this.course = course;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public double getBearing() {
		return bearing;
	}

	public void setBearing(double bearing) {
		this.bearing = bearing;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}
	
	public double getSatellites() {
		return satellites;
	}

	public void setSatellites(double satellites) {
		this.satellites = satellites;
	}

	public double getHdop() {
		return hdop;
	}

	public void setHdop(double hdop) {
		this.hdop = hdop;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getWifi() {
		return wifi;
	}

	public void setWifi(String wifi) {
		this.wifi = wifi;
	}

	public double getBatteryLevel() {
		return battery;
	}

	public void setBatteryLevel(double batteryLevel) {
		this.battery = batteryLevel;
	}

	public String getTextMessage() {
		return message;
	}

	public void setTextMessage(String textMessage) {
		this.message = textMessage;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getIr_temp() {
		return ir_temp;
	}

	public void setIr_temp(double ir_temp) {
		this.ir_temp = ir_temp;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public double getMbar() {
		return mbar;
	}

	public void setMbar(double mbar) {
		this.mbar = mbar;
	}

	public double getAccel_x() {
		return accel_x;
	}

	public void setAccel_x(double accel_x) {
		this.accel_x = accel_x;
	}

	public double getAccel_y() {
		return accel_y;
	}

	public void setAccel_y(double accel_y) {
		this.accel_y = accel_y;
	}

	public double getAccel_z() {
		return accel_z;
	}

	public void setAccel_z(double accel_z) {
		this.accel_z = accel_z;
	}

	public double getGyro_x() {
		return gyro_x;
	}

	public void setGyro_x(double gyro_x) {
		this.gyro_x = gyro_x;
	}

	public double getGyro_y() {
		return gyro_y;
	}

	public void setGyro_y(double gyro_y) {
		this.gyro_y = gyro_y;
	}

	public double getGyro_z() {
		return gyro_z;
	}

	public void setGyro_z(double gyro_z) {
		this.gyro_z = gyro_z;
	}

	public double getMagnet_x() {
		return magnet_x;
	}

	public void setMagnet_x(double magnet_x) {
		this.magnet_x = magnet_x;
	}

	public double getMagnet_y() {
		return magnet_y;
	}

	public void setMagnet_y(double magnet_y) {
		this.magnet_y = magnet_y;
	}

	public double getMagnet_z() {
		return magnet_z;
	}

	public void setMagnet_z(double magnet_z) {
		this.magnet_z = magnet_z;
	}

	public double getLight() {
		return light;
	}

	public void setLight(double light) {
		this.light = light;
	}

	public double getKeypress() {
		return keypress;
	}

	public void setKeypress(double keypress) {
		this.keypress = keypress;
	}

	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public double getAgentCount() {
		return agentCount;
	}

	public void setAgentCount(double agentCount) {
		this.agentCount = agentCount;
	}

	public boolean isMotion() {
		return motion;
	}

	public void setMotion(boolean motion) {
		this.motion = motion;
	}

	public void EventParser(String key, String value) {
		try {
			switch (key) {
			case "id":
			case "deviceid":
				setId(value);
				break;
			case "name":
				setName(value);
				break;
			case "event":
				setEvent(value);
				break;
			case "description":
				setDescription(value);
				break;
			case "process":
				setProcess(value);
				break;
			case "protocol":
				setProtocol(value);
				break;
			case "servertime":
				setServerTime(parseDate(value));
				break;
			case "timestamp":
				setDeviceTime(parseDate(value));
				break;
			case "fixtime":
				setFixTime(parseDate(value));
				break;
			case "outdated":
				setOutdated(Boolean.parseBoolean(value));
				break;
			case "valid":
				setValid(Boolean.parseBoolean(value));
				break;
			case "lat":
				setLat(Double.parseDouble(value));
				break;
			case "lon":
				setLon(Double.parseDouble(value));
				break;
			case "location":
				String[] location = value.split(",");
				setLat(Double.parseDouble(location[0]));
				setLon(Double.parseDouble(location[1]));
				break;
			case "altitude":
				setAltitude(Double.parseDouble(value));
				break;
			case "speed":
				setSpeed(Double.parseDouble(value));
				break;
			case "course":
				setCourse(Double.parseDouble(value));
				break;
			case "address":
				setAddress(value);
				break;
			case "accuracy":
				setAccuracy(Double.parseDouble(value));
				break;
			case "bearing":
			case "heading":
				setBearing(Double.parseDouble(value));
				break;
			case "network":
				setNetwork(value);
				break;
			case "sats":
			case "satellites":
				setSatellites(Double.parseDouble(value));
				break;
			case "hdop":
				setHdop(Double.parseDouble(value));
				break;
			case "cell":
				setCell(value);
				break;
			case "wifi":
				setWifi(value);
				break;
			case "batteryLevel":
			case "batt":
				setBatteryLevel(Double.parseDouble(value));
				break;
			case "message":
			case "textMessage":
				setTextMessage(value);
				break;
			case "temp":
				setTemp(Double.parseDouble(value));
				break;
			case "ir_temp":
				setIr_temp(Double.parseDouble(value));
				break;
			case "humidity":
				setHumidity(Double.parseDouble(value));
				break;
			case "mbar":
				setMbar(Double.parseDouble(value));
				break;
			case "accel_x":
				setAccel_x(Double.parseDouble(value));
				break;
			case "accel_y":
				setAccel_y(Double.parseDouble(value));
				break;
			case "accel_z":
				setAccel_z(Double.parseDouble(value));
				break;
			case "gyro_x":
				setGyro_x(Double.parseDouble(value));
				break;
			case "gyro_y":
				setGyro_y(Double.parseDouble(value));
				break;
			case "gyro_z":
				setGyro_z(Double.parseDouble(value));
				break;
			case "magnet_x":
				setMagnet_x(Double.parseDouble(value));
				break;
			case "magnet_y":
				setMagnet_y(Double.parseDouble(value));
				break;
			case "magnet_z":
				setMagnet_z(Double.parseDouble(value));
				break;
			case "light":
				setLight(Double.parseDouble(value));
				break;
			case "keypress":
				setKeypress(Double.parseDouble(value));
				break;
			case "alarm":
				setAlarm(value);
				break;
			case "distance":
				setDistance(Double.parseDouble(value));
				break;
			case "totalDistance":
				setTotalDistance(Double.parseDouble(value));
				break;
			case "agentCount":
				setAgentCount(Double.parseDouble(value));
				break;
			case "motion":
				setMotion(Boolean.parseBoolean(value));
				break;
			default:
				System.out.println("> Extended Event Token " + key + "=" + value + "|");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	private String parseDate(String date) {
		String sdate = "";
		try {
			long ldate = Long.parseLong(date);
			sdate = new java.text.SimpleDateFormat(DateFormat).format(new java.util.Date(ldate * 1000));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return sdate;
	}

	@Override
	public String toString() {
		String s = name + " - " + event;
		if (alarm != null && !alarm.isEmpty()) {
			return s = s + " - " + alarm;
		}
		if (message != null && !message.isEmpty()) {
			return s = s + " - " + message;
		}
		return s;
	}
}
