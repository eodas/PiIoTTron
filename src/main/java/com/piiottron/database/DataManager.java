package com.piiottron.database;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.piiottron.config.Config;
import com.piiottron.model.Event;

/**
 * This class manages and provides the set of allowed actions that can be
 * performed on the database. The actions are specific to the database, its
 * tables and schema.
 */

public class DataManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataManager.class);

	public static final String ACTION_SELECT_ALL = "selectAll";
	public static final String ACTION_SELECT = "select";
	public static final String ACTION_INSERT = "insert";
	public static final String ACTION_UPDATE = "update";
	public static final String ACTION_DELETE = "delete";

	private final Config config;
	private Connection connection;
	private boolean generateQueries;
	private boolean forceLdap;

	public DataManager(Config config) throws Exception {
		this.config = config;

		initDatabase();
		initDatabaseSchema();
	}

	private void initDatabase() throws Exception {
		String driverFile = config.getString("database.driverFile");
		if (driverFile != null) {
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			try {
				Method method = classLoader.getClass().getDeclaredMethod("addURL", URL.class);
				method.setAccessible(true);
				method.invoke(classLoader, new File(driverFile).toURI().toURL());
			} catch (NoSuchMethodException e) {
				Method method = classLoader.getClass().getDeclaredMethod("appendToClassPathForInstrumentation",
						String.class);
				method.setAccessible(true);
				method.invoke(classLoader, driverFile);
			}
		}

		String driver = config.getString("database.driver");
		if (driver != null) {
			Class.forName(driver);
		}

		String DriverClassName = config.getString("database.driver");
		String JdbcUrl = config.getString("database.url");
		String userName = config.getString("database.user");
		String password = config.getString("database.password");
		String connectionInitSql = config.getString("database.checkConnection", "SELECT 1");
		long IdleTimeout = 600000;

		int maxPoolSize = config.getInteger("database.maxPoolSize");

		if (maxPoolSize != 0) {
			long setMaximumPoolSize = maxPoolSize;
		}

		generateQueries = config.getBoolean("database.generateQueries");

		System.out.println("Connecting to database...");
		connection = DriverManager.getConnection(JdbcUrl, userName, password);
		connection.setAutoCommit(true);
	}

	public void closeDatabase() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initDatabaseSchema() {
		if (config.hasKey("database.changelog")) {

			String DriverClassName = config.getString("database.driver");
			String JdbcUrl = config.getString("database.url");
			String userName = config.getString("database.user");
			String password = config.getString("database.password");
			String connectionInitSql = config.getString("database.checkConnection", "SELECT 1");
			long IdleTimeout = 600000;

			int maxPoolSize = config.getInteger("database.maxPoolSize");
		}
	}

	// Inserts
	/**
	 * Adds a newly created event to the database.
	 *
	 * @param vehicle the newly created event
	 * @return true if the transaction was successful, otherwise false.
	 */
	public boolean insterEvent(Event event) {
		String insert_Event = "INSERT INTO Event(id, name, events, description, process, protocols, "
				+ "serverTime, deviceTime, fixTime, outdated, valid, lat, lon, altitude, speed, "
				+ "course, address, accuracy, bearing, network, satellites, hdop, cell, wifi, battery, message, "
				+ "temps, ir_temp, humidity, mbar, accel_x, accel_y, accel_z, gyro_x, gyro_y, gyro_z, "
				+ "magnet_x, magnet_y, magnet_z, light, keypress, alarm, distance, totalDistance, agentCount, motion) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(insert_Event);
			pstmt.setString(1, event.getId());
			pstmt.setString(2, event.getName());
			pstmt.setString(3, event.getEvent());
			pstmt.setString(4, event.getDescription());
			pstmt.setString(5, event.getProcess());
			pstmt.setString(6, event.getProtocol());
			pstmt.setString(7, event.getServerTime());
			pstmt.setString(8, event.getDeviceTime());
			pstmt.setString(9, event.getFixTime());
			pstmt.setString(10, (event.isOutdated()) ? "T" : "F"); // result = testCondition ? trueValue : falseValue
			pstmt.setString(11, (event.isValid()) ? "T" : "F");
			pstmt.setDouble(12, event.getLat());
			pstmt.setDouble(13, event.getLon());
			pstmt.setDouble(14, event.getAltitude());
			pstmt.setDouble(15, event.getSpeed());
			pstmt.setDouble(16, event.getCourse());
			pstmt.setString(17, event.getAddress());
			pstmt.setDouble(18, event.getAccuracy());
			pstmt.setDouble(19, event.getBearing());
			pstmt.setString(20, event.getNetwork());
			pstmt.setDouble(21, event.getSatellites());
			pstmt.setDouble(22, event.getHdop());
			pstmt.setString(23, event.getCell());
			pstmt.setString(24, event.getWifi());
			pstmt.setDouble(25, event.getBatteryLevel());
			pstmt.setString(26, event.getTextMessage());
			pstmt.setDouble(27, event.getTemp());
			pstmt.setDouble(28, event.getIr_temp());
			pstmt.setDouble(29, event.getHumidity());
			pstmt.setDouble(30, event.getMbar());
			pstmt.setDouble(31, event.getAccel_x());
			pstmt.setDouble(32, event.getAccel_y());
			pstmt.setDouble(33, event.getAccel_z());
			pstmt.setDouble(34, event.getGyro_x());
			pstmt.setDouble(35, event.getGyro_y());
			pstmt.setDouble(36, event.getGyro_z());
			pstmt.setDouble(37, event.getMagnet_x());
			pstmt.setDouble(38, event.getMagnet_y());
			pstmt.setDouble(39, event.getMagnet_z());
			pstmt.setDouble(40, event.getLight());
			pstmt.setDouble(41, event.getKeypress());
			pstmt.setString(42, event.getAlarm());
			pstmt.setDouble(43, event.getDistance());
			pstmt.setDouble(44, event.getTotalDistance());
			pstmt.setDouble(45, event.getAgentCount());
			pstmt.setString(46, (event.isMotion()) ? "T" : "F");
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			error(e);
		}
		return false;
	}

	// Updates
	/**
	 * Update device information in the database.
	 *
	 * @args id the device ID and information
	 * @return true if the transaction was successful, otherwise false.
	 */
	public boolean updateDevice(String id, String ipAddress, String lastUpdate) {
		String update_Device = "UPDATE Device SET ipAddress = ?, lastUpdate = ? WHERE id = ?";
		try {
			PreparedStatement pstmt = null;
			pstmt = connection.prepareStatement(update_Device);
			pstmt.setString(3, id);
			pstmt.setString(1, ipAddress);
			pstmt.setString(2, lastUpdate);
			return pstmt.executeUpdate() == 1;
		} catch (Exception e) {
			error(e);
		}
		return false;
	}

	// Queries
	/**
	 * Query if the device exists in the database.
	 *
	 * @param id the device ID to check
	 * @return true if the device exists, otherwise false.
	 */
	public boolean selectDevice(String id) {
		String select_Device = "SELECT id FROM Device WHERE id = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(select_Device);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (Exception e) {
			error(e);
		}
		return false;
	}

	/**
	 * Query return the process if the device exists in the database.
	 *
	 * @param id the device ID to check
	 * @return true if the device exists, otherwise false.
	 */
	public String selectDeviceProcess(String id) {
		String select_Device = "SELECT process FROM Device WHERE id = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(select_Device);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("process");
			}
		} catch (Exception e) {
			error(e);
		}
		return "";
	}

	/**
	 * Query return the ipAddress if the device exists in the database.
	 *
	 * @param id the device ID to check
	 * @return true if the device exists, otherwise false.
	 */
	public String selectDeviceIP(String id) {
		String select_Device = "SELECT ipaddress FROM Device WHERE id = ?";
		try {
			PreparedStatement pstmt = connection.prepareStatement(select_Device);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("ipaddress");
			}
		} catch (Exception e) {
			error(e);
		}
		return "";
	}

	/**
	 * Retrieves all events by the given Primary Key Auto-increment from the
	 * database. Returns a list of events.
	 *
	 * @param event the events of the pk Auto-increment
	 * @return the list of events.
	 */
	public List<Event> getEventsServerTime(String serverTime) {
		String select_Event = "SELECT id, name, events, description, process, protocols, serverTime, deviceTime, fixTime, outdated, valid, lat, lon, "
				+ "altitude, speed, course, address, accuracy, bearing, network, satellites, hdop, cell, wifi, battery, message, "
				+ "temps, ir_temp, humidity, mbar, accel_x, accel_y, accel_z, gyro_x, gyro_y, gyro_z, magnet_x, magnet_y, magnet_z, "
				+ "light, keypress, alarm, distance, totalDistance, agentCount, motion FROM Event "
				+ "WHERE serverTime > ?";
		List<Event> events = new ArrayList<Event>();
		try {
			PreparedStatement pstmt = connection.prepareStatement(select_Event);
			pstmt.setString(1, serverTime);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				events.add(new Event(rs.getString("id"), rs.getString("name"), rs.getString("events"),
						rs.getString("description"), rs.getString("process"),

						rs.getString("protocols"), rs.getString("serverTime"), rs.getString("deviceTime"),
						rs.getString("fixTime"), (rs.getString("outdated").equals("T") ? true : false),
						(rs.getString("valid").equals("T") ? true : false),

						rs.getDouble("lat"), rs.getDouble("lon"), rs.getDouble("altitude"), rs.getDouble("speed"),
						rs.getDouble("course"), rs.getString("address"), rs.getDouble("accuracy"), rs.getDouble("bearing"),
						rs.getString("network"),

						rs.getDouble("satellites"), rs.getDouble("hdop"), rs.getString("cell"), rs.getString("wifi"), rs.getDouble("battery"),
						rs.getString("message"),

						rs.getDouble("temps"), rs.getDouble("ir_temp"), rs.getDouble("humidity"), rs.getDouble("mbar"),
						rs.getDouble("accel_x"), rs.getDouble("accel_y"), rs.getDouble("accel_z"), rs.getDouble("gyro_x"),
						rs.getDouble("gyro_y"), rs.getDouble("gyro_z"), rs.getDouble("magnet_x"), rs.getDouble("magnet_y"),
						rs.getDouble("magnet_z"),

						rs.getDouble("light"), rs.getDouble("keypress"), rs.getString("alarm"), rs.getDouble("distance"),
						rs.getDouble("totalDistance"), rs.getDouble("agentCount"), (rs.getString("motion").equals("T") ? true : false)));
			}
		} catch (Exception e) {
			error(e);
		}
		return events;
	}
	
	/**
	 * Print an error message on exception thrown.
	 *
	 * @param e the exception
	 */
	private void error(Exception e) {
		System.err.println(e.getClass().getName() + ": " + e.getMessage());
		e.printStackTrace(System.err);
	}
}