package com.piiottron.database;

import java.beans.Introspector;
import java.lang.reflect.Method;
import java.io.File;
import java.net.URL;
import java.sql.*;  
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.piiottron.config.Config;
import com.piiottron.model.Device;
import com.piiottron.model.Event;
import com.piiottron.model.User;

/**
 * This class manages and provides the set of allowed actions
 * that can be performed on the database. The actions are
 * specific to the database, its tables and schema.
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
                    Method method = classLoader.getClass()
                            .getDeclaredMethod("appendToClassPathForInstrumentation", String.class);
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

    /**
     * Adds a newly created event to the database.
     *
     * @param vehicle   the newly created event
     * @return          true if the transaction was successful, otherwise false.
     */
    
    public boolean insterEvent(Event event) {
    	String AddEvent = "INSERT INTO Event(id, name, events, description, process, protocol, serverTime, deviceTime, fixTime, outdated, "
      			+ "valid, lat, lon, altitude, speed, course, address, accuracy, bearing, network, "
      		    + "hdop, cell, wifi, battery, message, temps, ir_temp, humidity, mbar, "
      		    + "accel_x, accel_y, accel_z, gyro_x, gyro_y, gyro_z, magnet_x, magnet_y, magnet_z, "
      		    + "light, keypress, alarm, distance, totalDistance, agentCount, motion) "
    			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(AddEvent);
                pstmt.setString(1, event.getId());
                pstmt.setString(2, event.getName());
                pstmt.setString(3, event.getEvent());
                pstmt.setString(4, event.getDescription());
                pstmt.setString(5, event.getProcess());
                pstmt.setString(6, event.getProtocol());
                pstmt.setString(7, event.getServerTime());
                pstmt.setString(8, event.getDeviceTime());
                pstmt.setString(9, event.getFixTime());
                pstmt.setString(10, (event.isOutdated()) ? "T" : "F"); //result = testCondition ? trueValue : falseValue
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
                pstmt.setDouble(21, event.getHdop());
                pstmt.setString(22, event.getCell());
                pstmt.setString(23, event.getWifi());
                pstmt.setDouble(24, event.getBatteryLevel());
                pstmt.setString(25, event.getTextMessage());
                pstmt.setDouble(26, event.getTemp());
                pstmt.setDouble(27, event.getIr_temp());
                pstmt.setDouble(28, event.getHumidity());
                pstmt.setDouble(29, event.getMbar());
                pstmt.setDouble(30, event.getAccel_x());
                pstmt.setDouble(31, event.getAccel_y());
                pstmt.setDouble(32, event.getAccel_z());
                pstmt.setDouble(33, event.getGyro_x());
                pstmt.setDouble(34, event.getGyro_y());
                pstmt.setDouble(35, event.getGyro_z());
                pstmt.setDouble(36, event.getMagnet_x());
                pstmt.setDouble(37, event.getMagnet_y());
                pstmt.setDouble(38, event.getMagnet_z());
                pstmt.setDouble(39, event.getLight());
                pstmt.setDouble(40, event.getKeypress());
                pstmt.setString(41, event.getAlarm());
                pstmt.setDouble(42, event.getDistance());
                pstmt.setDouble(43, event.getTotalDistance());
                pstmt.setDouble(44, event.getAgentCount());
                pstmt.setString(45, (event.isMotion()) ? "T" : "F"); 
                pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            error(e);
        }
        return false;
    }
    
    public String constructObjectQuery(String action, Class<?> clazz, boolean extended) {
        switch (action) {
            case ACTION_INSERT:
            case ACTION_UPDATE:
                StringBuilder result = new StringBuilder();
                StringBuilder fields = new StringBuilder();
                StringBuilder values = new StringBuilder();

                Set<Method> methods = new HashSet<>(Arrays.asList(clazz.getMethods()));
                methods.removeAll(Arrays.asList(Object.class.getMethods()));
                for (Method method : methods) {
                    boolean skip;
                    if (extended) {
                        skip = false;
                    } else {
                        skip = true;
                    }
                    if (!skip && method.getName().startsWith("get") && method.getParameterTypes().length == 0) {
                        String name = Introspector.decapitalize(method.getName().substring(3));
                        if (action.equals(ACTION_INSERT)) {
                            fields.append(name).append(", ");
                            values.append(":").append(name).append(", ");
                        } else {
                            fields.append(name).append(" = :").append(name).append(", ");
                        }
                    }
                }
                fields.setLength(fields.length() - 2);
                if (action.equals(ACTION_INSERT)) {
                    values.setLength(values.length() - 2);
                    result.append("INSERT INTO ").append(getObjectsTableName(clazz)).append(" (");
                    result.append(fields).append(") ");
                    result.append("VALUES (").append(values).append(")");
                } else {
                    result.append("UPDATE ").append(getObjectsTableName(clazz)).append(" SET ");
                    result.append(fields);
                    result.append(" WHERE id = :id");
                }
                return result.toString();
            case ACTION_SELECT_ALL:
                return "SELECT * FROM " + getObjectsTableName(clazz);
            case ACTION_SELECT:
                return "SELECT * FROM " + getObjectsTableName(clazz) + " WHERE id = :id";
            case ACTION_DELETE:
                return "DELETE FROM " + getObjectsTableName(clazz) + " WHERE id = :id";
            default:
                throw new IllegalArgumentException("Unknown action");
        }
    }

    public String constructPermissionQuery(String action, Class<?> owner, Class<?> property) {
        switch (action) {
            case ACTION_SELECT_ALL:
                return "SELECT " + makeNameId(owner) + ", " + makeNameId(property) + " FROM "
                        + getPermissionsTableName(owner, property);
            case ACTION_INSERT:
                return "INSERT INTO " + getPermissionsTableName(owner, property)
                        + " (" + makeNameId(owner) + ", " + makeNameId(property) + ") VALUES (:"
                        + makeNameId(owner) + ", :" + makeNameId(property) + ")";
            case ACTION_DELETE:
                return "DELETE FROM " + getPermissionsTableName(owner, property)
                        + " WHERE " + makeNameId(owner) + " = :" + makeNameId(owner)
                        + " AND " + makeNameId(property) + " = :" + makeNameId(property);
            default:
                throw new IllegalArgumentException("Unknown action");
        }
    }

    private String getQuery(String key) {
        String query = config.getString(key);
        if (query == null) {
            LOGGER.info("Query not provided: " + key);
        }
        return query;
    }

    public String getQuery(String action, Class<?> clazz) {
        return getQuery(action, clazz, false);
    }

    public String getQuery(String action, Class<?> clazz, boolean extended) {
        String queryName;
        if (action.equals(ACTION_SELECT_ALL)) {
            queryName = "database.select" + clazz.getSimpleName() + "s";
        } else {
            queryName = "database." + action.toLowerCase() + clazz.getSimpleName();
            if (extended) {
                queryName += "Extended";
            }
        }
        String query = config.getString(queryName);
        if (query == null) {
            if (generateQueries) {
                query = constructObjectQuery(action, clazz, extended);
                config.setString(queryName, query);
            } else {
                LOGGER.info("Query not provided: " + queryName);
            }
        }

        return query;
    }

    public String getQuery(String action, Class<?> owner, Class<?> property) {
        String queryName;
        switch (action) {
            case ACTION_SELECT_ALL:
                queryName = "database.select" + owner.getSimpleName() + property.getSimpleName() + "s";
                break;
            case ACTION_INSERT:
                queryName = "database.link" + owner.getSimpleName() + property.getSimpleName();
                break;
            default:
                queryName = "database.unlink" + owner.getSimpleName() + property.getSimpleName();
                break;
        }
        String query = config.getString(queryName);
        if (query == null) {
            if (generateQueries) {
                query = constructPermissionQuery(action, owner,
                        property.equals(User.class) ? User.class : property);
                config.setString(queryName, query);
            } else {
                LOGGER.info("Query not provided: " + queryName);
            }
        }

        return query;
    }

    private String getPermissionsTableName(Class<?> owner, Class<?> property) {
        String propertyName = property.getSimpleName();
        if (propertyName.equals("ManagedUser")) {
            propertyName = "User";
        }
        return "tc_" + Introspector.decapitalize(owner.getSimpleName())
                + "_" + Introspector.decapitalize(propertyName);
    }

    private String getObjectsTableName(Class<?> clazz) {
        String result = "tc_" + Introspector.decapitalize(clazz.getSimpleName());
        // Add "s" ending if object name is not plural already
        if (!result.endsWith("s")) {
            result += "s";
        }
        return result;
    }

    private String makeNameId(Class<?> clazz) {
        String name = clazz.getSimpleName();
        return Introspector.decapitalize(name) + (!name.contains("Id") ? "Id" : "");
    }
 
/**
 * Print an error message on exception thrown.
 *
 * @param e     the exception
 */
private void error(Exception e) {
    System.err.println(e.getClass().getName() + ": " + e.getMessage());
    e.printStackTrace(System.err);
}

}